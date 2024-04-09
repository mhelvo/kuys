package nl.spindletree.annotation;


import com.google.common.collect.ImmutableList;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import nl.spindletree.kuys.domain.annotation.processor.DTOProcessor;

import javax.tools.JavaFileObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class DTOProcessorTest {

    private static final JavaFileObject HELLO_WORLD_RESOURCE =
            JavaFileObjects.forResource("test/HelloWorld.java");

    //    @Test
    public void process() throws IOException {
        DTOProcessor processor = new DTOProcessor();
        File jar = compileTestJar();
        // compile with only 'tmp.txt' on the annotation processor path
        Compilation compilation =
                javac()
//                        .withProcessors(processor)
                        .withAnnotationProcessorPath(ImmutableList.of(jar))
                        .compile(JavaFileObjects.forSourceLines("Test", "class Test {}"));
        assertThat(compilation).succeeded();


    }

    private static File compileTestJar() throws IOException {
        File file = File.createTempFile("tmp", ".jar");
        try (ZipOutputStream zipOutput = new ZipOutputStream(new FileOutputStream(file))) {
            ZipEntry entry = new ZipEntry("tmp.txt");
            zipOutput.putNextEntry(entry);
            zipOutput.closeEntry();
        }
        return file;
    }


}