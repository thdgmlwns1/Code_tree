import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static final int MAX_N = 1005;
    static final int MIN_VALUE = -1000000000;

    static int n, t;
    static int[][] a = new int[MAX_N][MAX_N];
    static int[][] mx = new int[MAX_N][MAX_N];
    static int[][][] dp = new int[MAX_N][MAX_N][2];

    static void calculateMaxProfit(int i, int j, int x, int y, int passedTime, int profit) {
        if (passedTime == t) {
            mx[i][j] = Math.max(mx[i][j], profit);
            return;
        }

        if (x + 1 <= n) {
            calculateMaxProfit(i, j, x + 1, y, passedTime + 1, profit + a[x + 1][y]);
        }

        if (y + 1 <= n) {
            calculateMaxProfit(i, j, x, y + 1, passedTime + 1, profit + a[x][y + 1]);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 1; j <= n; j++) {
                a[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                mx[i][j] = MIN_VALUE;
                dp[i][j][0] = MIN_VALUE;
                dp[i][j][1] = MIN_VALUE;
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                calculateMaxProfit(i, j, i, j, 0, a[i][j]);
            }
        }

        dp[1][1][0] = a[1][1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {

                dp[i][j][1] = Math.max(dp[i][j][1], dp[i][j][0] + mx[i][j]);

                if (i + 1 <= n) {
                    dp[i + 1][j][0] = Math.max(dp[i + 1][j][0], dp[i][j][0] + a[i + 1][j]);
                    dp[i + 1][j][1] = Math.max(dp[i + 1][j][1], dp[i][j][1] + a[i + 1][j]);
                }

                if (j + 1 <= n) {
                    dp[i][j + 1][0] = Math.max(dp[i][j + 1][0], dp[i][j][0] + a[i][j + 1]);
                    dp[i][j + 1][1] = Math.max(dp[i][j + 1][1], dp[i][j][1] + a[i][j + 1]);
                }
            }
        }

        System.out.println(Math.max(dp[n][n][0], dp[n][n][1]));
    }
}