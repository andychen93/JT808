import React, { useEffect, useMemo, useState } from 'react'
import { Button, Card, Form, Input, Layout, Menu, Space, Table, Tag, Typography } from 'antd'
import axios from 'axios'
import { useDispatch, useSelector } from 'react-redux'
import { addTelemetry, setAuth, setTelemetry, logout } from './store'

const { Header, Sider, Content } = Layout

function LoginForm() {
  const dispatch = useDispatch()
  const [loading, setLoading] = useState(false)

  const onFinish = async (values) => {
    setLoading(true)
    try {
      const { data } = await axios.post('/api/auth/login', values)
      dispatch(setAuth(data))
    } finally {
      setLoading(false)
    }
  }

  return (
    <Card title="平台登录" style={{ width: 360, margin: '80px auto' }}>
      <Form onFinish={onFinish}>
        <Form.Item name="username" rules={[{ required: true }]}><Input placeholder="用户名" /></Form.Item>
        <Form.Item name="password" rules={[{ required: true }]}><Input.Password placeholder="密码" /></Form.Item>
        <Button type="primary" htmlType="submit" loading={loading} block>登录</Button>
      </Form>
    </Card>
  )
}

function VehicleMap() {
  const list = useSelector(s => s.telemetry.list)
  const points = useMemo(() => list.map((t, i) => ({
    ...t,
    x: ((t.longitude || 0) - 70) * 8,
    y: 500 - ((t.latitude || 0) - 10) * 8,
    key: `${t.simNo}-${i}`
  })), [list])

  return (
    <Card title="车辆地图（SSE 实时）">
      <svg viewBox="0 0 800 500" style={{ width: '100%', border: '1px solid #eee', background: '#f6ffed' }}>
        {points.map(p => (
          <g key={p.key}>
            <circle cx={p.x} cy={p.y} r="6" fill="#1677ff" />
            <text x={p.x + 8} y={p.y - 8} fontSize="12">{p.simNo}</text>
          </g>
        ))}
      </svg>
    </Card>
  )
}

export default function App() {
  const dispatch = useDispatch()
  const { token, permissions, username } = useSelector(s => s.auth)
  const list = useSelector(s => s.telemetry.list)
  const [menu, setMenu] = useState('map')

  useEffect(() => {
    if (!token) return
    axios.get('/api/location/latest', { headers: { Authorization: `Bearer ${token}` } })
      .then(({ data }) => dispatch(setTelemetry(data)))
      .catch(() => {})

    const source = new EventSource('/api/sse/telemetry')
    source.addEventListener('telemetry', e => dispatch(addTelemetry(JSON.parse(e.data))))
    return () => source.close()
  }, [token, dispatch])

  if (!token) return <LoginForm />

  const canView = permissions.includes('vehicle:view')

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Sider>
        <Menu theme="dark" selectedKeys={[menu]} onClick={e => setMenu(e.key)} items={[
          { key: 'map', label: '车辆地图', disabled: !canView },
          { key: 'table', label: '车辆列表', disabled: !canView }
        ]} />
      </Sider>
      <Layout>
        <Header style={{ background: '#fff' }}>
          <Space>
            <Typography.Text>当前用户：{username}</Typography.Text>
            {permissions.map(p => <Tag key={p}>{p}</Tag>)}
            <Button onClick={() => dispatch(logout())}>退出</Button>
          </Space>
        </Header>
        <Content style={{ padding: 16 }}>
          {menu === 'map' && <VehicleMap />}
          {menu === 'table' && (
            <Table rowKey={(r, i) => `${r.simNo}-${i}`} dataSource={list}
              columns={[
                { title: '终端', dataIndex: 'simNo' },
                { title: '经度', dataIndex: 'longitude' },
                { title: '纬度', dataIndex: 'latitude' },
                { title: '速度', dataIndex: 'speedKmh' },
                { title: '协议', dataIndex: 'protocol' }
              ]}
            />
          )}
        </Content>
      </Layout>
    </Layout>
  )
}
