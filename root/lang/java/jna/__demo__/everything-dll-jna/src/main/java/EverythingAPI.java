import com.sun.jna.Library;
import com.sun.jna.Native;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

public interface EverythingAPI extends Library {
    static List<Result> query(String str, boolean matchCase, boolean matchWholeWord, boolean enableRegex) {
        List<Result> list = new ArrayList<>();
        Everything64.INSTANCE.Everything_SetSearchA(str);
        boolean success = Everything64.INSTANCE.Everything_QueryA(true);
        for (int i = 0; i < Everything64.INSTANCE.Everything_GetNumResults(); i++) {
            Result f = new Result();
            f.filename = Everything64.INSTANCE.Everything_GetResultFileNameA(i);
            f.location = Everything64.INSTANCE.Everything_GetResultPathA(i);
            f.isDirectory = Everything64.INSTANCE.Everything_IsFolderResult(i);
            list.add(f);
        }
        return list;
    }

    interface Everything64 extends Library {
        Everything64 INSTANCE = Native.load("Everything64", Everything64.class);
        void Everything_SetSearchA(String lpString);

        void Everything_SetMatchCase(boolean bEnable);
        void Everything_SetMatchWholeWord(boolean bEnable);
        void Everything_SetRegex(boolean bEnable);

        boolean Everything_QueryA(boolean bWait);

        int Everything_GetNumResults();

        boolean Everything_IsFolderResult(int dwIndex);
        boolean Everything_IsFileResult(int dwIndex);
        String Everything_GetResultFileNameA(int dwIndex);
        String Everything_GetResultPathA(int dwIndex);
    }

    @Setter
    class Options {
        private boolean matchCase;
        private boolean matchWholeWorld;
        private boolean enableRegex;
    }

    @ToString
    @Getter
    class Result {
        private String  filename;
        private String  location;
        private boolean isDirectory;
    }
}
