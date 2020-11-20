import network.ring.TokenRing;

import java.io.IOException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int shift = scanner.nextInt();
        TokenRing tokenRing = new TokenRing(n, m, shift);
        tokenRing.executeRing();
    }
}
