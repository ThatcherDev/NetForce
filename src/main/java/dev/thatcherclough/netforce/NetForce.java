package dev.thatcherclough.netforce;

import org.fusesource.jansi.AnsiConsole;

public class NetForce {

	private static String mode;
	private static String host;
	private static int port = 22;
	private static String user;
	private static String pass;
	private static int timeout = 300;
	final private static String help = "NetForce: A network scanning and SSH brute forcing tool (1.6.1)\n\nUsage:\n\tjava -jar netforce.jar [-h] [-v] [scan -t IPRANGE -p " +
			"PORT -w TIMEOUT]\n"
			+ "\t\t\t       [brute -t HOST -p PORT -un USER/FILE -pw PASSWORD/FILE -w TIMEOUT]\nArguments:\n\t-h,  --help\t\tDisplay this message.\n\t-v,  --version\t\tDisplay current version.\n"
			+ "\t-t,  --target\t\tSpecify IP range when scanning or host when brute forcing.\n\t-p,  --port\t\tSpecify port to use. (Set to 22 by default)\n"
			+ "\t-un, --user\t\tSpecify username or file containing usernames to use.\n\t-pw, --pass\t\tSpecify password or file containing passwords to use.\n"
			+ "\t-w,  --wait\t\tSpecify connection timeout in milliseconds. (Set to 300 by default)";

	/**
	 * Starts network scan or SSH brute force attack based on command line arguments
	 * {@link args}.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		try {
			AnsiConsole.systemInstall();
			if (args.length == 0)
				throw new Exception();
			for (int k = 0; k < args.length; k++) {
				if (args[k].equals("-h") || args[k].equals("--help")) {
					throw new Exception();
				} else if (args[k].equals("-v") || args[k].equals("--version")) {
					System.out.println(help.substring(0, help.indexOf("\n")));
					System.exit(0);
				} else if (args[k].equals("scan") || args[k].equals("brute"))
					mode = args[k];
				else if (args[k].equals("-t") || args[k].equals("--target"))
					host = args[++k];
				else if (args[k].equals("-p") || args[k].equals("--port"))
					port = Integer.parseInt(args[++k]);
				else if (args[k].equals("-un") || args[k].equals("--user"))
					user = args[++k];
				else if (args[k].equals("-pw") || args[k].equals("--pass"))
					pass = args[++k];
				else if (args[k].equals("-w") || args[k].equals("--wait"))
					timeout = Integer.parseInt(args[++k]);
			}
			if (mode == null || (mode.equals("scan") && host == null)
					|| (mode.equals("brute") && (host == null || user == null || pass == null)))
				throw new Exception();
			if (mode.equals("scan"))
				NetScan.scan(host, port, timeout);
			else if (mode.equals("brute"))
				Bruteforce.start(host, port, user, pass, timeout);
		} catch (Exception e) {
			System.out.println(help);
			System.exit(0);
		}
	}
}