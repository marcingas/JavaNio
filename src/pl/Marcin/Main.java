package pl.Marcin;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

try(FileOutputStream binFile = new FileOutputStream("data.dat");
    FileChannel binChannel = binFile.getChannel()){

    ByteBuffer buffer = ByteBuffer.allocate(100);
    //chained version:
//    byte[] outputBytes = "Hello World!".getBytes();
//    byte[] outputBytes2= "Nice to meet You".getBytes();
//    buffer.put(outputBytes).putInt(245).putInt(-98765).put(outputBytes2).putInt(1000);
//    buffer.flip();

//unchained version:
    byte[] outputBytes = "Hello World!".getBytes();

    buffer.put(outputBytes);
    long int1Pos = outputBytes.length;
    buffer.putInt(245);
    long int2Pos = int1Pos + Integer.BYTES;
    buffer.putInt(-98765);
    byte[] outputBytes2= "Nice to meet You".getBytes();
    buffer.put(outputBytes2);
    long int3Pos = int2Pos + Integer.BYTES + outputBytes2.length;
    buffer.putInt(1000);
    buffer.flip();

    binChannel.write(buffer);//writting all previous data from buffer in one shot
//    reading from data.dat




//read data in a random fashion
    RandomAccessFile ra = new RandomAccessFile("data.dat","rwd");
    FileChannel channel = ra.getChannel();
    ByteBuffer readBuffer = ByteBuffer.allocate(Integer.BYTES);
    channel.position(int3Pos);
    channel.read(readBuffer);
    readBuffer.flip();
    System.out.println("int3 = " + readBuffer.getInt());

    readBuffer.flip();
    channel.position(int2Pos);
    channel.read(readBuffer);
    readBuffer.flip();
    System.out.println("int2 = " + readBuffer.getInt());

    readBuffer.flip();
    channel.position(int1Pos);
    channel.read(readBuffer);
    readBuffer.flip();
    System.out.println("int1 = " + readBuffer.getInt());

    //copy data :
    RandomAccessFile copyFile = new RandomAccessFile("datacopy.dat","rw");
    FileChannel copyChannel = copyFile.getChannel();
    channel.position(0);
//    long numTransferred = copyChannel.transferFrom(channel,0,channel.size());
       long numTransferred = channel.transferTo(0,channel.size(),copyChannel);

    System.out.println("numTransferred = " + numTransferred );

    channel.close();
    ra.close();
    copyChannel.close();



//    write data in a random fashion:
//    byte[]outputString = "Hello".getBytes();
//    long str1Pos = 0;
//    long newInt1Pos = outputString.length;
//    long newInt2Pos = newInt1Pos + Integer.BYTES;
//    byte[] outputString2 = "Nice to".getBytes();
//    long str2Pos = newInt2Pos + Integer.BYTES;
//    long newInt3Pos = str2Pos + outputString2.length;
//
//    ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);
//    intBuffer.putInt(245);
//    intBuffer.flip();
//    binChannel.position(newInt1Pos);
//    binChannel.write(intBuffer);
//
//    intBuffer.flip();
//    intBuffer.putInt(-98765);
//    intBuffer.flip();
//    binChannel.position(newInt2Pos);
//    binChannel.write(intBuffer);
//
//    intBuffer.flip();
//    intBuffer.putInt(1000);
//    intBuffer.flip();
//    binChannel.position(newInt3Pos);
//    binChannel.write(intBuffer);
//
//    binChannel.position(str1Pos);
//    binChannel.write(ByteBuffer.wrap(outputString));
//    binChannel.position(str2Pos);
//    binChannel.write(ByteBuffer.wrap(outputString2));


//    ByteBuffer readBuffer = ByteBuffer.allocate(100);
//    channel.read(readBuffer);
//    readBuffer.flip();

//    byte[] inputString = new byte[outputBytes.length];
//    readBuffer.get(inputString);
//    System.out.println("InputString = " + new String(inputString));
//    System.out.println("Int1 = " + readBuffer.getInt());
//    System.out.println("Int2 ="+ readBuffer.getInt());
//    byte[] inputString2 = new byte[outputBytes2.length];
//    readBuffer.get(inputString2);
//    System.out.println("InputString2 = " + new String(inputString2));
//    System.out.println("int3 =" + readBuffer.getInt());

//    ByteBuffer buffer = ByteBuffer.allocate(outputBytes.length);
//    buffer.put(outputBytes);
//    buffer.flip();
//    int numBytes = binChannel.write(buffer);
//    System.out.println("numBytes written was : " + numBytes);
//    ByteBuffer intBuffer= ByteBuffer.allocate(Integer.BYTES);
//    intBuffer.putInt(245);
//    intBuffer.flip();
//    numBytes = binChannel.write(intBuffer);
//    System.out.println("numBytes written was : " + numBytes);
//    intBuffer.flip();
//    intBuffer.putInt(-98765);
//    intBuffer.flip();
//    numBytes = binChannel.write(intBuffer);
//    System.out.println("numBytes written was : " + numBytes);
//
////    RandomAccessFile ra = new RandomAccessFile("data.dat", "rwd");
////    byte[] b = new byte[outputBytes.length];
////    ra.read(b);
////    System.out.println(new String(b));
////
////    long int1 = ra.readInt();
////    long int2 = ra.readInt();
////    System.out.println(int1);
////    System.out.println(int2);
////    reading with JAva.INO:
//    RandomAccessFile ra = new RandomAccessFile("data.dat", "rwd");
//    FileChannel channel = ra.getChannel();
//    outputBytes[0] = 'a';
//    outputBytes[1]= 'b';
//    buffer.flip();
//    long numBytesRead = channel.read(buffer);
//    if(buffer.hasArray()){
//        System.out.println("byte buffer " + new String(buffer.array()));
////        System.out.println("byte buffer changed " + new String(outputBytes));
//    }
////    Absolute read
//    intBuffer.flip();
//    numBytesRead = channel.read(intBuffer);
//    System.out.println(intBuffer.getInt(0));
//    intBuffer.flip();
//    numBytesRead = channel.read(intBuffer);
//    intBuffer.flip();
//    System.out.println(intBuffer.getInt(0));
//    System.out.println(intBuffer.getInt());
//
////    relative read
////    intBuffer.flip();
////    numBytesRead = channel.read(intBuffer);
////    intBuffer.flip();
////    System.out.println(intBuffer.getInt());
////    intBuffer.flip();
////    numBytesRead = channel.read(intBuffer);
////    intBuffer.flip();
////    System.out.println(intBuffer.getInt());
//    channel.close();
//    ra.close();
////    System.out.println("outputbytes = " + new String(outputBytes));
//
//
//
//////    FileInputStream file = new FileInputStream("data.txt");
//////    FileChannel channel = file.getChannel();
////    Path dataPath = FileSystems.getDefault().getPath("data.txt");
////    Files.write(dataPath,"\nLine 5".getBytes("UTF-8"), StandardOpenOption.APPEND);
////    List<String> lines = Files.readAllLines(dataPath);
////    for(String line : lines){
////        System.out.println(line);
////    }

}catch (IOException e){
    e.printStackTrace();
}
    }
}