package io.edfs.tskandroidv2;


public class TSKJni {

    static {
        System.load("/opt/dapp/uploadPath/so/libTSKLinux.so");
    }


    public static native int Init();

    public static native int LoginUser(byte[] nIID);

    public static native int SetHoldIdentity(int nListVerb, byte[] nIdentityID, short nPermissionInfo, int nLastTime, byte[] jbyteArray, int nKeyBufLen);

    public static native byte[] DigestSha256Single(int nflowlen, byte[] buffer);

    public static native byte[] IdentityIssueGetPrivateKey(byte[] jbyteArray, int nSeedLen);


    public static native String IdentityIssueExBase64(byte[] jbyteArray, int nSeedLen);

    public static native byte[] IdentityGetPublicKeyByPrivateKey(byte[] jbyteArray, int nSeedLen);

    public static native byte[] FileOpTaskAdjustByFlow(byte[] pHeadSrc,
                                                       int nHeadFlowSrcLen,
                                                       boolean bDelAdd,
                                                       byte[] nIID,
                                                       short nPermission,
                                                       int nLastTime,
                                                       byte[] pKeyBuf,
                                                       int nKeyBufLen
    );
}
