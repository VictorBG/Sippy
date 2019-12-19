package prop.algorithms.jpeg;

import java.util.HashMap;

public class Huffman {

  public static final HashMap<String, String> DC0MAP = new HashMap<String, String>() {{
    put("00", "00");
    put("01", "010");
    put("02", "011");
    put("03", "100");
    put("04", "101");
    put("05", "110");
    put("06", "1110");
    put("07", "11110");
    put("08", "111110");
    put("09", "1111110");
    put("0A", "11111110");
    put("0B", "111111110");
  }};
  public static final HashMap<String, String> DC1MAP = new HashMap<String, String>() {{
    put("00", "00");
    put("01", "01");
    put("02", "10");
    put("03", "110");
    put("04", "1110");
    put("05", "11110");
    put("06", "111110");
    put("07", "1111110");
    put("08", "11111110");
    put("09", "111111110");
    put("0A", "1111111110");
    put("0B", "11111111110");
  }};
  public static final HashMap<String, String> AC0MAP = new HashMap<String, String>() {{
    put("01", "00");
    put("02", "01");
    put("03", "100");
    put("00", "1010");
    put("04", "1011");
    put("11", "1100");
    put("05", "11010");
    put("12", "11011");
    put("21", "11100");
    put("31", "111010");
    put("41", "111011");
    put("06", "1111000");
    put("13", "1111001");
    put("51", "1111010");
    put("61", "1111011");
    put("07", "11111000");
    put("22", "11111001");
    put("71", "11111010");
    put("14", "111110110");
    put("32", "111110111");
    put("81", "111111000");
    put("91", "111111001");
    put("A1", "111111010");
    put("08", "1111110110");
    put("23", "1111110111");
    put("42", "1111111000");
    put("B1", "1111111001");
    put("C1", "1111111010");
    put("15", "11111110110");
    put("52", "11111110111");
    put("D1", "11111111000");
    put("F0", "11111111001");
    put("24", "111111110100");
    put("33", "111111110101");
    put("62", "111111110110");
    put("72", "111111110111");
    put("82", "111111111000000");
    put("09", "1111111110000010");
    put("0A", "1111111110000011");
    put("16", "1111111110000100");
    put("17", "1111111110000101");
    put("18", "1111111110000110");
    put("19", "1111111110000111");
    put("1A", "1111111110001000");
    put("25", "1111111110001001");
    put("26", "1111111110001010");
    put("27", "1111111110001011");
    put("28", "1111111110001100");
    put("29", "1111111110001101");
    put("2A", "1111111110001110");
    put("34", "1111111110001111");
    put("35", "1111111110010000");
    put("36", "1111111110010001");
    put("37", "1111111110010010");
    put("38", "1111111110010011");
    put("39", "1111111110010100");
    put("3A", "1111111110010101");
    put("43", "1111111110010110");
    put("44", "1111111110010111");
    put("45", "1111111110011000");
    put("46", "1111111110011001");
    put("47", "1111111110011010");
    put("48", "1111111110011011");
    put("49", "1111111110011100");
    put("4A", "1111111110011101");
    put("53", "1111111110011110");
    put("54", "1111111110011111");
    put("55", "1111111110100000");
    put("56", "1111111110100001");
    put("57", "1111111110100010");
    put("58", "1111111110100011");
    put("59", "1111111110100100");
    put("5A", "1111111110100101");
    put("63", "1111111110100110");
    put("64", "1111111110100111");
    put("65", "1111111110101000");
    put("66", "1111111110101001");
    put("67", "1111111110101010");
    put("68", "1111111110101011");
    put("69", "1111111110101100");
    put("6A", "1111111110101101");
    put("73", "1111111110101110");
    put("74", "1111111110101111");
    put("75", "1111111110110000");
    put("76", "1111111110110001");
    put("77", "1111111110110010");
    put("78", "1111111110110011");
    put("79", "1111111110110100");
    put("7A", "1111111110110101");
    put("83", "1111111110110110");
    put("84", "1111111110110111");
    put("85", "1111111110111000");
    put("86", "1111111110111001");
    put("87", "1111111110111010");
    put("88", "1111111110111011");
    put("89", "1111111110111100");
    put("8A", "1111111110111101");
    put("92", "1111111110111110");
    put("93", "1111111110111111");
    put("94", "1111111111000000");
    put("95", "1111111111000001");
    put("96", "1111111111000010");
    put("97", "1111111111000011");
    put("98", "1111111111000100");
    put("99", "1111111111000101");
    put("9A", "1111111111000110");
    put("A2", "1111111111000111");
    put("A3", "1111111111001000");
    put("A4", "1111111111001001");
    put("A5", "1111111111001010");
    put("A6", "1111111111001011");
    put("A7", "1111111111001100");
    put("A8", "1111111111001101");
    put("A9", "1111111111001110");
    put("AA", "1111111111001111");
    put("B2", "1111111111010000");
    put("B3", "1111111111010001");
    put("B4", "1111111111010010");
    put("B5", "1111111111010011");
    put("B6", "1111111111010100");
    put("B7", "1111111111010101");
    put("B8", "1111111111010110");
    put("B9", "1111111111010111");
    put("BA", "1111111111011000");
    put("C2", "1111111111011001");
    put("C3", "1111111111011010");
    put("C4", "1111111111011011");
    put("C5", "1111111111011100");
    put("C6", "1111111111011101");
    put("C7", "1111111111011110");
    put("C8", "1111111111011111");
    put("C9", "1111111111100000");
    put("CA", "1111111111100001");
    put("D2", "1111111111100010");
    put("D3", "1111111111100011");
    put("D4", "1111111111100100");
    put("D5", "1111111111100101");
    put("D6", "1111111111100110");
    put("D7", "1111111111100111");
    put("D8", "1111111111101000");
    put("D9", "1111111111101001");
    put("DA", "1111111111101010");
    put("E1", "1111111111101011");
    put("E2", "1111111111101100");
    put("E3", "1111111111101101");
    put("E4", "1111111111101110");
    put("E5", "1111111111101111");
    put("E6", "1111111111110000");
    put("E7", "1111111111110001");
    put("E8", "1111111111110010");
    put("E9", "1111111111110011");
    put("EA", "1111111111110100");
    put("F1", "1111111111110101");
    put("F2", "1111111111110110");
    put("F3", "1111111111110111");
    put("F4", "1111111111111000");
    put("F5", "1111111111111001");
    put("F6", "1111111111111010");
    put("F7", "1111111111111011");
    put("F8", "1111111111111100");
    put("F9", "1111111111111101");
    put("FA", "1111111111111110");
  }};
  public static final HashMap<String, String> AC1MAP = new HashMap<String, String>() {{
    put("00", "00");
    put("01", "01");
    put("02", "100");
    put("03", "1010");
    put("11", "1011");
    put("04", "11000");
    put("05", "11001");
    put("21", "11010");
    put("31", "11011");
    put("06", "111000");
    put("12", "111001");
    put("41", "111010");
    put("51", "111011");
    put("07", "1111000");
    put("61", "1111001");
    put("71", "1111010");
    put("13", "11110110");
    put("22", "11110111");
    put("32", "11111000");
    put("81", "11111001");
    put("08", "111110100");
    put("14", "111110101");
    put("42", "111110110");
    put("91", "111110111");
    put("A1", "111111000");
    put("B1", "111111001");
    put("C1", "111111010");
    put("09", "1111110110");
    put("23", "1111110111");
    put("33", "1111111000");
    put("52", "1111111001");
    put("F0", "1111111010");
    put("15", "11111110110");
    put("62", "11111110111");
    put("72", "11111111000");
    put("D1", "11111111001");
    put("0A", "111111110100");
    put("16", "111111110101");
    put("24", "111111110110");
    put("34", "111111110111");
    put("E1", "11111111100000");
    put("25", "111111111000010");
    put("F1", "111111111000011");
    put("17", "1111111110001000");
    put("18", "1111111110001001");
    put("19", "1111111110001010");
    put("1A", "1111111110001011");
    put("26", "1111111110001100");
    put("27", "1111111110001101");
    put("28", "1111111110001110");
    put("29", "1111111110001111");
    put("2A", "1111111110010000");
    put("35", "1111111110010001");
    put("36", "1111111110010010");
    put("37", "1111111110010011");
    put("38", "1111111110010100");
    put("39", "1111111110010101");
    put("3A", "1111111110010110");
    put("43", "1111111110010111");
    put("44", "1111111110011000");
    put("45", "1111111110011001");
    put("46", "1111111110011010");
    put("47", "1111111110011011");
    put("48", "1111111110011100");
    put("49", "1111111110011101");
    put("4A", "1111111110011110");
    put("53", "1111111110011111");
    put("54", "1111111110100000");
    put("55", "1111111110100001");
    put("56", "1111111110100010");
    put("57", "1111111110100011");
    put("58", "1111111110100100");
    put("59", "1111111110100101");
    put("5A", "1111111110100110");
    put("63", "1111111110100111");
    put("64", "1111111110101000");
    put("65", "1111111110101001");
    put("66", "1111111110101010");
    put("67", "1111111110101011");
    put("68", "1111111110101100");
    put("69", "1111111110101101");
    put("6A", "1111111110101110");
    put("73", "1111111110101111");
    put("74", "1111111110110000");
    put("75", "1111111110110001");
    put("76", "1111111110110010");
    put("77", "1111111110110011");
    put("78", "1111111110110100");
    put("79", "1111111110110101");
    put("7A", "1111111110110110");
    put("82", "1111111110110111");
    put("83", "1111111110111000");
    put("84", "1111111110111001");
    put("85", "1111111110111010");
    put("86", "1111111110111011");
    put("87", "1111111110111100");
    put("88", "1111111110111101");
    put("89", "1111111110111110");
    put("8A", "1111111110111111");
    put("92", "1111111111000000");
    put("93", "1111111111000001");
    put("94", "1111111111000010");
    put("95", "1111111111000011");
    put("96", "1111111111000100");
    put("97", "1111111111000101");
    put("98", "1111111111000110");
    put("99", "1111111111000111");
    put("9A", "1111111111001000");
    put("A2", "1111111111001001");
    put("A3", "1111111111001010");
    put("A4", "1111111111001011");
    put("A5", "1111111111001100");
    put("A6", "1111111111001101");
    put("A7", "1111111111001110");
    put("A8", "1111111111001111");
    put("A9", "1111111111010000");
    put("AA", "1111111111010001");
    put("B2", "1111111111010010");
    put("B3", "1111111111010011");
    put("B4", "1111111111010100");
    put("B5", "1111111111010101");
    put("B6", "1111111111010110");
    put("B7", "1111111111010111");
    put("B8", "1111111111011000");
    put("B9", "1111111111011001");
    put("BA", "1111111111011010");
    put("C2", "1111111111011011");
    put("C3", "1111111111011100");
    put("C4", "1111111111011101");
    put("C5", "1111111111011110");
    put("C6", "1111111111011111");
    put("C7", "1111111111100000");
    put("C8", "1111111111100001");
    put("C9", "1111111111100010");
    put("CA", "1111111111100011");
    put("D2", "1111111111100100");
    put("D3", "1111111111100101");
    put("D4", "1111111111100110");
    put("D5", "1111111111100111");
    put("D6", "1111111111101000");
    put("D7", "1111111111101001");
    put("D8", "1111111111101010");
    put("D9", "1111111111101011");
    put("DA", "1111111111101100");
    put("E2", "1111111111101101");
    put("E3", "1111111111101110");
    put("E4", "1111111111101111");
    put("E5", "1111111111110000");
    put("E6", "1111111111110001");
    put("E7", "1111111111110010");
    put("E8", "1111111111110011");
    put("E9", "1111111111110100");
    put("EA", "1111111111110101");
    put("F2", "1111111111110110");
    put("F3", "1111111111110111");
    put("F4", "1111111111111000");
    put("F5", "1111111111111001");
    put("F6", "1111111111111010");
    put("F7", "1111111111111011");
    put("F8", "1111111111111100");
    put("F9", "1111111111111101");
    put("FA", "1111111111111110");
  }};

}
