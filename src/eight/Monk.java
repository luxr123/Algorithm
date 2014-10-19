package eight;
/**
 * 某寺庙里7个和尚每天轮流挑水，每个人可以挑水的日期是固定的
 * 
 * <pre>
 * 和尚1： 星期二，四； 
 * 和尚2： 星期一，六； 
 * 和尚3： 星期三，日； 
 * 和尚4： 星期五 
 * 和尚5： 星期一，四，六； 
 * 和尚6： 星期二，五； 
 * 和尚7： 星期三，六，日。
 * </pre>
 * 
 * 需要给出所有的挑水方案
 * 
 */
public class Monk {

	// 每行代表一个和尚
	// ready[i][j]=1表示第i个和尚第j天有空。
	static int[][] ready = {
			{ 0, 1, 0, 1, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 1, 0 },
			{ 0, 0, 1, 0, 0, 0, 1 },
			{ 0, 0, 0, 0, 1, 0, 0 },
			{ 1, 0, 0, 1, 0, 1, 0 },
			{ 0, 1, 0, 0, 1, 0, 0 },
			{ 0, 0, 1, 0, 0, 1, 1 }
		};
	
	static boolean check(int col) {
		for (int i = 0; i < col; i++) {
			int diff = Math.abs(ret[i] - ret[col]);
			if (diff == 0 )
				return false;
		}
		return true;
	}

	
	static int[] ret = new int[7];

	static int duty(int col) {
		int ways = 0;
		if(col==7){
			printMonk();
			return 1;
		}
		for (int row = 0; row < 7; row++) {
			if (ready[row][col] != 1)
				continue;
			ret[col] = row;
			if (check(col)) {
				ways += duty(col + 1);
			}
		}

		return ways;
	}
	
	private static void printMonk() {
		for(int i=0; i<ret.length;i++){
			System.out.format("%d ",ret[i]+1);
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		System.out.println(duty(0));
	}
}
