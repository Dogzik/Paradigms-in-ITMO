import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//easy modification
public class CalcSHA256 {
    public static void main(String[] args) {
        try {
            MessageDigest sha256Digest = MessageDigest.getInstance("SHA-256");
            if (args.length > 1) {
                System.err.println("Incorrect arguments input");
            } else if (args.length == 0) {
                try {
                    byte[] in = new byte[4096];
                    int amount = System.in.read(in);
                    //System.out.println(amount);
                    while (amount > 0) {
                        sha256Digest.update(in, 0, amount);
                        amount = System.in.read(in);
                    }
                    byte[] sum = sha256Digest.digest();
                    String hexSum = javax.xml.bind.DatatypeConverter.printHexBinary(sum);
                    System.out.println(hexSum + " -");
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                try (FileInputStream file = new FileInputStream(args[0])) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(file));
                    String line = in.readLine();
                    while (line != null) {
                        try {
                            sha256Digest.update(Files.readAllBytes(Paths.get(line)));
                            byte[] sum = sha256Digest.digest();
                            String hexSum = javax.xml.bind.DatatypeConverter.printHexBinary(sum);
                            System.out.println(hexSum);
                            sha256Digest.reset();
                        } catch (SecurityException e) {
                            System.err.println("Security exception occurred while accessing to " + line);
                        } catch (OutOfMemoryError e) {
                            System.err.println("File " + line + " is too big");
                        } catch (InvalidPathException e) {
                            System.err.println("File/path name - \"" + line + "\" is not correct");
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        } finally {
                            line = in.readLine();
                        }
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No such hash-algorithm");
        }
    }
}
