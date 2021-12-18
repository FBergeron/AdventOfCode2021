import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day16 {

    class Packet {

        public Packet(String str) {
            binaryValue = new StringBuilder(str);
            for (int i = 0; i < str.length(); i++)
                binaryValue.append(hex2bin(str.charAt(i)));

            cursor = 0;

            readVersionHeader();
            readTypeIdHeader();

            if (typeId == 4)
                readLiteralValue();
            else
                readSubpackets();
            binaryValue.delete(cursor, binaryValue.length());
        }

        private void readVersionHeader() {
            String strVersionHeader = binaryValue.substring(cursor, cursor + 3);
            cursor += 3;
            version = Integer.valueOf(strVersionHeader, 2);
        }

        private void readTypeIdHeader() {
            String strTypeIdHeader = binaryValue.substring(cursor, cursor + 3);
            cursor += 3;
            typeId = Integer.valueOf(strTypeIdHeader, 2);
        }

        private void readLiteralValue() {
            StringBuilder strBinaryValue = new StringBuilder();

            String strLastValue;
            do {
                String strValuePart = binaryValue.substring(cursor, cursor + 5);
                strLastValue = strValuePart.substring(0, 1);
                String strNumValuePart = strValuePart.substring(1, 5);
                strBinaryValue.append(strNumValuePart);
                cursor += 5;
            }
            while ("1".equals(strLastValue));

            literalValue = Long.valueOf(strBinaryValue.toString(), 2);
        }

        private void readSubpackets() {
            String strLengthTypeId = binaryValue.substring(cursor, cursor + 1);
            cursor += 1;

            if ("0".equals(strLengthTypeId)) {
                String strTotalLength = binaryValue.substring(cursor, cursor + 15);
                int totalLength = Integer.valueOf(strTotalLength, 2);
                cursor += 15;

                int end = cursor + totalLength;
                while (cursor < end) {
                    Packet child = new Packet(binaryValue.substring(cursor, cursor + totalLength));
                    cursor += child.getLength();
                    children.add(child);
                }
            }
            else {
                String strSubPacketCount = binaryValue.substring(cursor, cursor + 11);
                int subPacketCount = Integer.valueOf(strSubPacketCount, 2);
                cursor += 11;

                for (int i = 0; i < subPacketCount; i++) {
                    Packet child = new Packet(binaryValue.substring(cursor));
                    cursor += child.getLength();
                    children.add(child);
                }
            }
        }

        public int getLength() {
            return binaryValue.length();
        }

        public String toBinaryString() {
            return binaryValue.toString();
        }

        public String toString() {
            StringBuilder str = new StringBuilder();
            str.append("{");
            str.append("version: " + version + "; ");
            str.append("typeId: " + typeId + "; ");
            str.append("literalValue: " + literalValue + "; ");
            str.append("children: " + children + ";");
            str.append("}");
            return str.toString();
        }

        public int getVersionSum() {
            int sum = 0;
            sum += version;
            for (Packet child : children)
                sum += child.getVersionSum();
            return sum;
        }

        public long getValue() {
            if (literalValue != null)
                return literalValue.longValue();

            if (typeId == 0) {
                long sum = 0;
                for (Packet child : children)
                    sum += child.getValue();
                return sum;
            }
            else if (typeId == 1) {
                long product = 1;
                for (Packet child : children)
                    product *= child.getValue();
                return product;
            }
            else if (typeId == 2) {
                long minimum = Long.MAX_VALUE;
                for (Packet child : children) {
                    if (child.getValue() < minimum)
                        minimum = child.getValue();
                }
                return minimum;
            }
            else if (typeId == 3) {
                long maximum = Long.MIN_VALUE;
                for (Packet child : children) {
                    if (child.getValue() > maximum)
                        maximum = child.getValue();
                }
                return maximum;
            }
            else if (typeId == 5)
                return (children.get(0).getValue() > children.get(1).getValue() ? 1 : 0);
            else if (typeId == 6)
                return (children.get(0).getValue() < children.get(1).getValue() ? 1 : 0);
            else if (typeId == 7)
                return (children.get(0).getValue() == children.get(1).getValue() ? 1 : 0);

            return -1;
        }

        public Long literalValue;
        public int version;
        public int typeId;
        public ArrayList<Packet> children = new ArrayList<Packet>();

        private StringBuilder binaryValue;
        private int cursor;

    }

    public Day16() {
    }

    private String readInput(String filename) {
        String input = null;
        File f = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(f);
            BufferedReader bufReader = new BufferedReader(reader);
            boolean isFirstSectionOver = false;
            String line = bufReader.readLine();
            input = line;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return input;
    }

    public void run() {
        //String input = readInput("input_test_1.txt");
        //String input = readInput("input_test_2.txt");
        //String input = readInput("input_test_3.txt");
        //String input = readInput("input_test_4.txt");
        //String input = readInput("input_test_5.txt");
        //String input = readInput("input_test_6.txt");
        //String input = readInput("input_test_7.txt");
        //String input = readInput("input_test_8.txt");
        //String input = readInput("input_test_9.txt");
        String input = readInput("input.txt");

        StringBuilder binaryInput = new StringBuilder();
        for (int i = 0; i < input.length(); i++)
            binaryInput.append(hex2bin(input.charAt(i)));
        System.out.println("binaryInput=" + binaryInput);

        Packet p = new Packet(binaryInput.toString());
        System.out.println( "p="+p );
        //System.out.println( "versionSum="+p.getVersionSum() );
        System.out.println( "value="+p.getValue() );
    }

    private String hex2bin(char c) {
        if (c == '0')
            return "0000";
        else if (c == '1')
            return "0001";
        else if (c == '2')
            return "0010";
        else if (c == '3')
            return "0011";
        else if (c == '4')
            return "0100";
        else if (c == '5')
            return "0101";
        else if (c == '6')
            return "0110";
        else if (c == '7')
            return "0111";
        else if (c == '8')
            return "1000";
        else if (c == '9')
            return "1001";
        else if (c == 'A')
            return "1010";
        else if (c == 'B')
            return "1011";
        else if (c == 'C')
            return "1100";
        else if (c == 'D')
            return "1101";
        else if (c == 'E')
            return "1110";
        else if (c == 'F')
            return "1111";
        else
            return null;
    }

    public static void main(String[] args) {
        Day16 d = new Day16();
        d.run();
    }
}
