package fr.niixx.melchior;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;

import fr.niixx.melchior.cli.Cli;

public class Main implements Registry {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, InterruptedException {
		
		String pwd = System.getProperty("user.dir") + '\\';
		File autoexec_file = new File(pwd + "autoexec");
		if (!autoexec_file.exists()) {
			ClassLoader cLoad = Class.forName("fr.niixx.melchior.Main").getClassLoader();
			InputStream is = cLoad.getResourceAsStream("autoexec");
			Files.copy(is, autoexec_file.toPath());
			System.out.println("Please fill the autoexec file");
			System.exit(0);
		}
		
	    FileInputStream autoexec = new FileInputStream("./autoexec");
		new Cli(null, autoexec);
		
		new Cli(System.out, System.in);
	}
	
}