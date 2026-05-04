import java.io.*;
import java.util.*;

public class Main {

    // 각 칸의 정보: 높이, 좌표
    static class Cell {
        int h, x, y;

        Cell(int h, int x, int y) {
            this.h = h;
            this.x = x;
            this.y = y;
        }
    }

    static int N, K;
    static int[][] board;
    static Cell[] cells;

    // 상하좌우
    static final int[] dx = {-1, 1, 0, 0};
    static final int[] dy = {0, 0, -1, 1};

    /**
     * limit 이하의 높이 차만 허용할 때,
     * 길이 K 이상의 "증가하는 경로"가 존재하는지 판별
     *
     * 경로 조건:
     * 1) 상하좌우 인접 칸으로 이동
     * 2) 다음 칸의 높이가 더 커야 함 (strictly increasing)
     * 3) 높이 차가 limit 이하여야 함
     *
     * 아이디어:
     * - 높이가 작은 칸 -> 큰 칸 으로만 이동 가능하므로 그래프는 DAG
     * - 높이 오름차순으로 칸들을 처리하면서 DP
     * - dp[x][y] = (x,y)에서 끝나는 최장 증가 경로 길이
     */
    static boolean check(int limit) {
        int[][] dp = new int[N][N];

        // 모든 칸은 자기 자신만 방문하는 길이 1짜리 경로 가능
        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], 1);
        }

        int best = 1; // 전체 최장 경로 길이

        // 높이 오름차순으로 처리
        for (Cell cur : cells) {
            int x = cur.x;
            int y = cur.y;
            int h = cur.h;

            // 현재 칸에서 갈 수 있는 "더 높은" 인접 칸으로 전이
            for (int dir = 0; dir < 4; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];

                // 격자 밖이면 무시
                if (nx < 0 || nx >= N || ny < 0 || ny >= N) continue;

                int nh = board[nx][ny];

                // 다음 칸은 반드시 더 높아야 하고,
                // 그 높이 차가 limit 이하여야 이동 가능
                if (nh > h && nh - h <= limit) {
                    dp[nx][ny] = Math.max(dp[nx][ny], dp[x][y] + 1);
                    best = Math.max(best, dp[nx][ny]);
                }
            }
        }

        // 길이 K 이상의 경로를 만들 수 있으면 true
        return best >= K;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        board = new int[N][N];
        cells = new Cell[N * N];

        int idx = 0;

        // 입력
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int h = Integer.parseInt(st.nextToken());
                board[i][j] = h;
                cells[idx++] = new Cell(h, i, j);
            }
        }

        // 높이 오름차순 정렬
        // 같은 높이는 이동 불가이므로 순서는 크게 상관없음
        Arrays.sort(cells, Comparator.comparingInt(c -> c.h));

        // limit을 아무리 크게 잡아도 길이 K 이상 증가 경로 자체가 없으면 -1
        if (!check(100_000_000)) {
            System.out.println(-1);
            return;
        }

        // 답 = "허용 가능한 최대 높이 차"의 최솟값
        // 이를 이분 탐색
        int lo = 0;
        int hi = 100_000_000;
        int ans = hi;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            if (check(mid)) {
                // mid로 가능하면 더 작은 값도 가능한지 왼쪽 탐색
                ans = mid;
                hi = mid - 1;
            } else {
                // mid로 불가능하면 더 큰 값이 필요
                lo = mid + 1;
            }
        }

        System.out.println(ans);
    }
}
