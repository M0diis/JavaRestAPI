package me.m0dii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SpringBootApplication
public class RestApiApplication {

    public static void main(String[] args) {
        JFrame frame = new JFrame("RestApiApplication");
        JButton exitButton = new JButton("EXIT");

        generateFrame(frame, exitButton);

        ConfigurableApplicationContext run = SpringApplication.run(RestApiApplication.class, args);

        addListeners(frame, exitButton, run);
    }

    private static void addListeners(JFrame frame, JButton exitButton, ConfigurableApplicationContext run) {
        exitButton.addActionListener(e -> {
            run.close();
            System.exit(0);
        });


        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                run.close();
                System.exit(0);
            }
        });
    }

    private static void generateFrame(JFrame frame, JButton exitButton) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(exitButton, BorderLayout.CENTER);

        frame.add(panel);

        frame.setSize(200, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
}
