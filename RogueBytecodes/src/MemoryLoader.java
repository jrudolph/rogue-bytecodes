import java.io.IOException;



public class MemoryLoader {
	public static void int2bytes(int i,byte[] ar){
		ar[3] = (byte)((i>>24) & 0xff);
		ar[2] = (byte)((i>>16) & 0xff);
		ar[1] = (byte)((i>>8) & 0xff);
		ar[0] = (byte)(i & 0xff);
	}
	
	public static void main(String[] args) throws IOException {
		Object o = new byte[]{
				(byte)0xaa
				,(byte)0xbb
				,(byte)0xcc
				,(byte)0xdd
				,(byte)0xee
				,(byte)0xff};//new Object();
		
		byte[] res = new byte[4];
		
		Object test = "das ist das haus des nikolaus".getBytes();
		test.hashCode();
		
		int pos = MemoryTools.addressOfObject(test);
		
		for (int i=0;i<=0x30;i+=4){
			int2bytes(MemoryTools.readInt(pos+i),res);
			System.out.write(res);
		}
	}
}
