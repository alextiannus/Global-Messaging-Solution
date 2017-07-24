import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * Created by Donnie on 2017/2/20.
 */
public class Test1 {

    @Test
    public void test() {
        JsonObject jsonObject = new JsonObject();
        String enc = DigestUtils.md5Hex("000000");
        System.out.print(enc);
    }
}
