package hpe.com.SecIoT.encryption;

import com.voltage.securedata.enterprise.FPE;
import com.voltage.securedata.enterprise.LibraryContext;
import com.voltage.securedata.enterprise.VeException;
/**
 * Created by vrettos on 26.09.2016.
 * used to connect with the Voltage Appliance
 */
public class CryptoEngine {
    //TODO read this info from a config file
    private static final String LIBRARY_NAME    ="vibesibmplejava";
    private static final String policyURL       = "https://voltage-pp-0000.dataprotection.voltage.com/policy/clientPolicy.xml";
    private static final String identity        = "accounts22@dataprotection.voltage.com";
    private static final String sharedSecret    = "voltage123";
    private static final String format          = "SSN";
    private static final String trustStorePath  = "../trustStore";
    private static final String cachePath       = "../cache";
    // TODO: Check the multi-threading behaviour of these objects, as well as object creation time.
    private static LibraryContext library       = null;
    private static FPE fpe                      = null;

    static {
        // Load the JNI library
		/* TODO: When running this thing, set java.library.path correctly via a -D command line param.
		 * When running Tomcat in Eclipse, go to Servers, select your Tomcat, configure launch parameters
		 * When running Tomcat stand-alone
		 *    on Windows, go to Java options in the Tomcat management app
		 *    on Linux, modify catalina.sh
		 */
        System.out.print("Current value of java.library.path: ");
        System.out.println(System.getProperty("java.library.path"));
        System.out.println("Running on " + System.getProperty("os.name"));
        System.out.print("Loading library " + LIBRARY_NAME + " ... ");
        System.loadLibrary(LIBRARY_NAME);
        System.out.println("success!");

        // Dev Guide Code Snippet: VERSION; LC:2
        // Print the API version
        System.out.println("SimpleAPI version: " + LibraryContext.getVersion());
        System.out.println();

        // Sample configuration

        try {
            // Dev Guide Code Snippet: LIBCTXBUILD; LC:6  Create the context for crypto operations
            System.out.print("Constructing library object... ");
            library = new LibraryContext.Builder()
                    .setPolicyURL(policyURL)
/*
 * Even though we're running on Windows, we need a trustStorePath!!!!!
 * Java on Linux -> follow the manual, manage certificates through trustStorePath
 * C# on Windows -> not tried, but likely guess: manage certificates through Windows
 * Java on Windows -> manual: treat like Windows what actually works: treat like Linux *
 */
                    .setFileCachePath(cachePath)
                    .setTrustStorePath(trustStorePath)
                    .build();
            System.out.println("success!");

            // Dev Guide Code Snippet: FPEBUILD; LC:5
            // Protect and access the credit card number
            System.out.print("Constructing fpe object... ");
            fpe = library.getFPEBuilder(format)
                    .setSharedSecret(sharedSecret)
                    .setIdentity(identity)
                    .build();
            System.out.println("success!");



        } catch (Throwable ex) {
            System.err.println("Failed: Unexpected exception" + ex);
            System.err.println("All future Voltage API calls will fail!!!");
            ex.printStackTrace();
        }

    }

    public String encrypt(String plainText) {
        if(fpe!=null) {
            synchronized (fpe) {
                try {
                    return fpe.protect(plainText);
                } catch (VeException ex) {
                    System.err.println("Error during Voltage encryption");
                    ex.printStackTrace();
                    return "***FAILED***";
                }
            }
        }else{
            System.out.println("fpe undefined - error was thrown earlier");
            return "***FAILED***";
        }
    }

    public String decrypt(String encryptedText){
        if(fpe!=null) {
            synchronized (fpe) {
                try {
                    return fpe.access(encryptedText);
                } catch (VeException ex) {
                    System.err.println("Error during Voltage decrytpion");
                    ex.printStackTrace();
                    return "***FAILED***";
                }
            }
        }else{
            System.out.println("fpe undefined - error was thrown earlier");
            return "***FAILED***";
        }
    }

}
