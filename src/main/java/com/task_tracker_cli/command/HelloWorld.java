package com.task_tracker_cli.command;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@ShellCommandGroup("HelloWorld")
public class HelloWorld {
    @ShellMethod("Say Hello World")
    public void hello() {
        System.out.println("Hello, World!");
    }
}
