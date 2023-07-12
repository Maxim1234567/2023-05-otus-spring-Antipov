package ru.otus.service.impl;

import ru.otus.service.IOService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceStreams implements IOService {
    private final PrintStream output;
    private final Scanner input;

    public IOServiceStreams(PrintStream output, InputStream input) {
        this.output = output;
        this.input = new Scanner(input);
    }

    @Override
    public void println(String line) {
        output.println(line);
    }

    @Override
    public void print(String line) {
        output.print(line);
    }

    @Override
    public String readLine() {
        return input.nextLine();
    }
}
