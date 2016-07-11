/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoppingserver;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
public class QR {
    public static void generateqr(String barcode , String path)
    {
        ByteArrayOutputStream out = QRCode.from(barcode).to(ImageType.PNG).stream();

		try {
                    String _path=path+barcode+".PNG";
			FileOutputStream fout = new FileOutputStream(new File(_path));

			fout.write(out.toByteArray());

			fout.flush();
			fout.close();

		} catch (FileNotFoundException e)
                {
			System.out.println(e.toString());
		} catch (IOException e) {
			System.out.println(e.toString());
		}
    }
    
}
