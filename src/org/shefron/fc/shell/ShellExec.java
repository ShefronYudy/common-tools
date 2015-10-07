package org.shefron.fc.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellExec {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath = "/opt/BOCO/test/test/ShellExec/start.sh";
		String[] shellArr = { "/bin/sh", "-c", filePath };
		String[] env = { "shefron=bluesky", "yudy=netsky" };
		Process process = null;
		BufferedReader br = null, br2 = null;
		try {
			// 修改文件权限
			Runtime.getRuntime().exec("chmod 777 " + filePath);

			process = Runtime.getRuntime().exec(shellArr, env);
			// 错误执行信息
			int flag = process.waitFor();
			if (flag != 0) {
				System.out.println("==========异常输出==========");
				br = new BufferedReader(new InputStreamReader(process
						.getErrorStream()));
				while (br.read() != -1) {
					System.out.println(br.readLine());
				}
			}
			System.out.println("==========正常输出==========");
			// 正常执行反馈消息
			br2 = new BufferedReader(new InputStreamReader(process
					.getInputStream()));
			String lineStr = "";
			while ((lineStr = br2.readLine()) != null) {
				System.out.println("##################" + lineStr);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (br2 != null) {
					br2.close();
				}
				if (process.getErrorStream() != null) {
					process.getErrorStream().close();
				}
				if (process.getInputStream() != null) {
					process.getInputStream().close();
				}
				if (process.getOutputStream() != null) {
					process.getOutputStream().close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
