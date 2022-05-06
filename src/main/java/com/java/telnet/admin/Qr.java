package com.java.telnet.admin;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.java.telnet.DB;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Qr {

    static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

//    public static void main(String[] args) throws SQLException, IOException, WriterException {
//        DB db=new DB();
//        PreparedStatement ps = db.connect().prepareStatement("UPDATE login_info SET qr=? where matricule=?");
//        PreparedStatement p = db.connect().prepareStatement("select matricule from login_info ");
//        ResultSet rs=p.executeQuery();
//        while(rs.next()){
//            ps.setBytes(1,getQRCodeImage(rs.getString(1),250,250));
//            ps.setString(2,rs.getString(1));
//
//        }
//        p.close();ps.executeUpdate();ps.close();
//    }
}