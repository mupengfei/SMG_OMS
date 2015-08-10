package sh.odb.plugin.media.message;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RSYNCTask extends AbstractTask {

	private String rsyncSH;

	private String rcommand;
	private String rcharacter;

	private String[] rcommands;
	private String[] rcharacters;

	private BufferedReader inBr;
	private BufferedInputStream in;

	@Override
	public void done() throws IOException {
		if(inBr!=null   ){
			try {
				inBr.close();
				inBr=null;
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		if( in!=null){ 
			in.close();
			in=null;
		}
		
	}

	@Override
	protected String execut() throws Exception {
		String[] commands = super.getMeta("command").split("&&");
		String[] com = new String[commands.length + 1];
		com[0] = rsyncSH;
		for (int i = 0; i < commands.length; i++) {
			com[i + 1] = filter(commands[i].trim());
		}
		return exec(com);
	}

	private String filter(String str) throws Exception {
		if (!exist(str, this.rcommands)) {
			throw new Exception("command is not on the white list:" + str);
		}
		for (String character : this.rcharacters) {
			str = str.replace("\\" + character, character).replace(character, "\\" + character);
		}
		return str;
	}

	private boolean exist(String str, String[] strs) {
		for (String command : strs) {
			if (str.startsWith(command)) {
				return true;
			}
		}
		return false;
	}

	private String exec(String[] commands) throws Exception {
		StringBuilder message = new StringBuilder();
		Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
		Process p = run.exec(commands);// 启动另一个进程来执行命令
		in = new BufferedInputStream(p.getInputStream());
		inBr = new BufferedReader(new InputStreamReader(in));
		String lineStr;
		while ((lineStr = inBr.readLine()) != null) {
			message.append(lineStr + "\r\n");
		}

		// 检查命令是否执行失败。
		if (p.waitFor() != 0) {
			if (p.exitValue() == 1)// p.exitValue()==0表示正常结束，1：非正常结束
				message.append("action error \r\n");
		}

		done();

		if (message.toString().trim().length() == 0) {
			throw new Exception("message is null \r\n");
		}
		return message.toString();
	}

	public String getRsyncSH() {
		return rsyncSH;
	}

	public void setRsyncSH(String rsyncSH) {
		this.rsyncSH = rsyncSH;
	}

	public String getRcommand() {
		return rcommand;
	}

	public void setRcommand(String rcommand) {
		this.rcommand = rcommand;
		this.rcommands = rcommand.split(",");
	}

	public String getRcharacter() {
		return rcharacter;
	}

	public void setRcharacter(String rcharacter) {
		this.rcharacter = rcharacter;
		this.rcharacters = rcharacter.split(",");
	}

}
