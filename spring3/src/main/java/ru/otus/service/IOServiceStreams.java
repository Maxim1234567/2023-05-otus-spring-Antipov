package ru.otus.service;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceStreams implements IOService {
    private final PrintStream output;
    private final Scanner input;

    public IOServiceStreams(PrintStream output, InputStream input) {
        this.output = output;
        this.input = new Scanner(input);
    }

    @Override
    public void println(String line) {
        System.out.println(line);
    }

    @Override
    public void print(String line) {
        System.out.print(line);
    }

    @Override
    public String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
