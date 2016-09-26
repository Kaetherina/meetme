package hpe.com.SecIoT.servlet;

import hpe.com.SecIoT.encryption.CryptoEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by vrettos on 26.09.2016.
 * Testservlet to test Cryptoengine in early phases
 */
@WebServlet("/test")
public class TestServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private CryptoEngine engine = new CryptoEngine();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String plainText = request.getParameter("q");
        response.setContentType("text/plain");
        try(PrintWriter pw = new PrintWriter(response.getWriter())){
            pw.println("Served at: " + request.getContextPath());
            pw.println("Reading plainText from request parameter 'q'");
            pw.println("Got input: " + plainText);
            String cipherText = engine.encrypt(plainText);
            pw.println("Resulting cipherText: " + cipherText);
            pw.println("...");
            pw.println("...");
            pw.println("now decrypting the ciphertext: " + cipherText);
            String oldPlain = engine.decrypt(cipherText);
            pw.println("here goes the old plaintext: " + oldPlain);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }
}
