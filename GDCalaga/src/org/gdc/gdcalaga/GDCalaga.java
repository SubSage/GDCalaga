package org.gdc.gdcalaga;

import java.io.File;
import java.lang.reflect.Field;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class GDCalaga {

	static final int SCREEN_SIZE_X = 1280;
	static final int SCREEN_SIZE_Y = 720;
	
	static {
		String path = System.getProperties().get("java.library.path").toString();

		String os = System.getProperty("os.name").toLowerCase();

		if (os.indexOf("win") >= 0) {
			path = path + File.pathSeparator + "." + File.separator + "libs" + File.separator + "windows";
		}
		else if (os.indexOf("mac") >= 0) {
			path = path + File.pathSeparator + "./libs/macosx";
		}
		else if (os.indexOf("linux") >= 0) {
			path = path + File.pathSeparator + "./libs/linux";
		}
		System.out.println(path);
		System.getProperties().setProperty("java.library.path", path);
		
		try {
			// This hack allows dynamically updating library path for different platforms
			Field fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
			fieldSysPath.setAccessible( true );
			fieldSysPath.set( null, null );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new GDCalagaGame());
			container.setDisplayMode(SCREEN_SIZE_X, SCREEN_SIZE_Y, false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
