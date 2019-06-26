package logbook.manager;

import logbook.model.LogEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FileManager {

    private static final File DATA = new File("story.dat");
    private static final File PASS = new File("pass.dat");
    private static final String ENCRYPTION_ALGORITHM = "AES";

    private SecretKeySpec secretKey;
    private Cipher cipher;

    @PostConstruct
    public void init() throws NoSuchPaddingException, NoSuchAlgorithmException {
        try {
            byte[] pass = getPassword();
            this.secretKey = new SecretKeySpec(pass, ENCRYPTION_ALGORITHM);
            this.cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
            FileUtils.writeByteArrayToFile(PASS, pass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getPassword() {
        try {
            return FileUtils.readFileToByteArray(PASS);
        } catch (FileNotFoundException e) {
            // do nothing
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RandomStringUtils.random(16, true, true).getBytes();
    }

    public Map<String, LogEntry> loadFile() {
        return readObject().stream().collect(Collectors.toMap(LogEntry::getId, entry -> entry, (a, b) -> b, LinkedHashMap::new));
    }

    private LinkedList<LogEntry> readObject() {
        try {
            this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
            if (DATA.exists()) {
                ObjectInputStream in = new ObjectInputStream(new CipherInputStream(new FileInputStream(DATA), cipher));
                return (LinkedList<LogEntry>) in.readObject();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new LinkedList<>();
    }

    public void saveFile(Collection<LogEntry> entries) {
        LinkedList<LogEntry> list = new LinkedList<>(entries);
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
            ObjectOutputStream out = new ObjectOutputStream(new CipherOutputStream(new FileOutputStream(DATA), cipher));
            out.writeObject(list);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
