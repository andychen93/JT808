package com.example.jt808.parser;

public final class HexUtil {
    private HexUtil() {}

    public static byte[] hexToBytes(String hex) {
        String normalized = hex.replace(" ", "").replace("\n", "").replace("\r", "");
        if ((normalized.length() & 1) != 0) {
            throw new IllegalArgumentException("Hex string length must be even");
        }
        byte[] out = new byte[normalized.length() / 2];
        for (int i = 0; i < normalized.length(); i += 2) {
            out[i / 2] = (byte) Integer.parseInt(normalized.substring(i, i + 2), 16);
        }
        return out;
    }

    public static int u16(byte[] src, int idx) {
        return ((src[idx] & 0xFF) << 8) | (src[idx + 1] & 0xFF);
    }

    public static long u32(byte[] src, int idx) {
        return ((long) (src[idx] & 0xFF) << 24)
                | ((long) (src[idx + 1] & 0xFF) << 16)
                | ((long) (src[idx + 2] & 0xFF) << 8)
                | (src[idx + 3] & 0xFFL);
    }
}
