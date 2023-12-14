package com.esay.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.esay.App;

public class EsayFrame extends JFrame {
    private JTextArea logTextArea;
    private JTextField inputField;
    private String inputText;

    public EsayFrame (App app) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Dummy frame.");
        this.setSize(1000, 700);
        // this.setResizable(false);
        this.setIconImage(new ImageIcon("anya.png").getImage());
        this.setLayout(new GridBagLayout());

        //// Client or server option label
        JLabel label1 = new JLabel();
        label1.setVisible(true);
        label1.setText("Choose your side.");
        label1.setIcon(new ImageIcon("anya.png"));
        label1.setFont(new Font("Font1", Font.ITALIC, 20));
        // label1.setBackground(Color.BLACK);
        // label1.setOpaque(true);
        // label1.setMaximumSize(new Dimension(100, 100));
        Border border = BorderFactory.createLineBorder(Color.GRAY, 20, rootPaneCheckingEnabled);
        label1.setBorder(border);

        // Positioning
        label1.setVerticalAlignment(JLabel.TOP);
        label1.setHorizontalAlignment(JLabel.LEFT);
        this.add(label1);
        //// Client or server option label END

        //// Input pane

        // Create a JTextField for input
        inputField = new JTextField();
        GridBagConstraints inputConstraints = new GridBagConstraints();
        inputConstraints.gridx = 0;
        inputConstraints.gridy = 2;
        inputConstraints.weighty = 1.0; // Allow the log pane to expand vertically
        inputConstraints.fill = GridBagConstraints.BOTH;
        inputConstraints.insets = new Insets(10, 10, 10, 10);
        this.add(inputField, inputConstraints);
        //// Input pane END

        //// Buttons

        // Button
        JButton serverButton = new JButton("Server");
        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Server button Clicked!");
                app.loopFlag = false;
                app.startServer();
            }
        });

        JButton clientButton = new JButton("Client");
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Client button Clicked!");
                app.loopFlag = false;
                app.startClient();
            }
        });

        // Create a button to submit input
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When the button is clicked, redirect the input
                inputText = inputField.getText();
                inputField.setText(null);
                System.out.println("Submit button pressed!");
            }
        });

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new GridBagLayout());

        GridBagConstraints button1Constraints = new GridBagConstraints();
        button1Constraints.gridx = 0;
        button1Constraints.gridy = 0;
        button1Constraints.insets = new Insets(10, 5, 10, 5);
        buttonsPanel.add(serverButton, button1Constraints);

        GridBagConstraints button2Constraints = new GridBagConstraints();
        button2Constraints.gridx = 1;
        button2Constraints.gridy = 0;
        button2Constraints.insets = new Insets(10, 5, 10, 0);
        buttonsPanel.add(clientButton, button2Constraints);

        GridBagConstraints button3Constraints = new GridBagConstraints();
        button3Constraints.gridx = 2;
        button3Constraints.gridy = 0;
        button3Constraints.insets = new Insets(10, 5, 10, 0);
        buttonsPanel.add(submitButton, button3Constraints);

        // Add buttons panel to the frame
        GridBagConstraints buttonsPanelConstraints = new GridBagConstraints();
        buttonsPanelConstraints.gridx = 0;
        buttonsPanelConstraints.gridy = 3;
        buttonsPanelConstraints.insets = new Insets(10, 10, 10, 10);
        this.add(buttonsPanel, buttonsPanelConstraints);
        //// Buttons END

        //// Log pane
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logTextArea);

        // Add log pane to the frame
        GridBagConstraints logConstraints = new GridBagConstraints();
        logConstraints.gridx = 0;
        logConstraints.gridy = 1;
        logConstraints.weighty = 1.0; // Allow the log pane to expand vertically
        logConstraints.fill = GridBagConstraints.BOTH;
        logConstraints.insets = new Insets(10, 10, 10, 10);
        this.add(scrollPane, logConstraints);
        //// Log pane END



        //// Render window
        this.setVisible(true);

        redirectSystemOut();
    }


    private void redirectSystemOut() {
        PrintStream printStream = new PrintStream(new CustomOutputStream(logTextArea));
        System.setOut(printStream);
    }

    // CustomOutputStream to redirect System.out to JTextArea
    private static class CustomOutputStream extends PrintStream {
        private JTextArea textArea;

        public CustomOutputStream(JTextArea textArea) {
            super(System.out);
            this.textArea = textArea;
        }

        @Override
        public void write(byte[] buf, int off, int len) {
            final String message = new String(buf, off, len);
            textArea.append(message);
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }

    // Block until received input
    public String getInputText() {
        while(true) {
            if (this.inputText != null) {
                String ret = new String(this.inputText);
                this.inputText = null;
                return ret;
            }
            try {
                Thread.sleep(3000);
                // System.out.println("Waiting for input.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
