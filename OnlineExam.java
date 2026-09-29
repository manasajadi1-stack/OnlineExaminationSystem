import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OnlineExam extends JFrame implements ActionListener {

    ArrayList<Question> questions = new ArrayList<>();

    JLabel questionLabel;
    JLabel timerLabel;

    JRadioButton option1;
    JRadioButton option2;
    JRadioButton option3;
    JRadioButton option4;

    ButtonGroup buttonGroup;

    JButton previousButton;
    JButton nextButton;
    JButton submitButton;

    int currentQuestion = 0;
    int[] answers = new int[10];

    Timer timer;
    int timeLeft = 60;

    OnlineExam() {

        // Adding questions
        questions.add(new Question(
                "Which language is used for Android development?",
                "Java", "HTML", "CSS", "SQL", 1));

        questions.add(new Question(
                "Which keyword is used to create a class in Java?",
                "function", "class", "object", "new", 2));

        questions.add(new Question(
                "Which of the following is not a Java feature?",
                "Object-Oriented", "Platform Independent",
                "Pointer Support", "Secure", 3));

        questions.add(new Question(
                "Which collection is used to store dynamic data?",
                "ArrayList", "String", "Scanner", "JFrame", 1));

        questions.add(new Question(
                "Which method is the starting point of a Java program?",
                "start()", "run()", "main()", "begin()", 3));

        questions.add(new Question(
                "Which keyword is used for inheritance in Java?",
                "this", "super", "extends", "inherit", 3));

        questions.add(new Question(
                "Which component is used for a single-line text input?",
                "JLabel", "JTextField", "JButton", "JFrame", 2));

        questions.add(new Question(
                "Which component allows only one option to be selected?",
                "JLabel", "JTextField", "ButtonGroup", "JFrame", 3));

        questions.add(new Question(
                "Which package contains Swing components?",
                "java.io", "java.util", "javax.swing", "java.sql", 3));

        questions.add(new Question(
                "Which class is used to display a message box?",
                "JOptionPane", "JFrame", "JLabel", "JPanel", 1));

        // Initialize answers
        for (int i = 0; i < answers.length; i++) {
            answers[i] = 0;
        }

        // Window settings
        setTitle("Online Examination System");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Timer label
        timerLabel = new JLabel("Time Remaining: 60 seconds");
        timerLabel.setBounds(450, 20, 200, 30);
        panel.add(timerLabel);

        // Question label
        questionLabel = new JLabel();
        questionLabel.setBounds(30, 60, 620, 50);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(questionLabel);

        // Radio buttons
        option1 = new JRadioButton();
        option2 = new JRadioButton();
        option3 = new JRadioButton();
        option4 = new JRadioButton();

        option1.setBounds(50, 130, 550, 30);
        option2.setBounds(50, 170, 550, 30);
        option3.setBounds(50, 210, 550, 30);
        option4.setBounds(50, 250, 550, 30);

        panel.add(option1);
        panel.add(option2);
        panel.add(option3);
        panel.add(option4);

        // Button group
        buttonGroup = new ButtonGroup();

        buttonGroup.add(option1);
        buttonGroup.add(option2);
        buttonGroup.add(option3);
        buttonGroup.add(option4);

        // Buttons
        previousButton = new JButton("Previous");
        nextButton = new JButton("Next");
        submitButton = new JButton("Submit");

        previousButton.setBounds(50, 320, 120, 40);
        nextButton.setBounds(200, 320, 120, 40);
        submitButton.setBounds(350, 320, 120, 40);

        panel.add(previousButton);
        panel.add(nextButton);
        panel.add(submitButton);

        // Button events
        previousButton.addActionListener(this);
        nextButton.addActionListener(this);
        submitButton.addActionListener(this);

        add(panel);

        // Display first question
        displayQuestion();

        // Timer
        timer = new Timer(1000, new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                timeLeft--;

                timerLabel.setText(
                        "Time Remaining: " + timeLeft + " seconds");

                if (timeLeft <= 0) {

                    timer.stop();

                    JOptionPane.showMessageDialog(
                            OnlineExam.this,
                            "Time is over! Exam will be submitted.");

                    calculateResult();
                }
            }
        });

        timer.start();

        setVisible(true);
    }

    // Display question
    void displayQuestion() {

        Question q = questions.get(currentQuestion);

        questionLabel.setText(
                "Q" + (currentQuestion + 1) + ". " + q.getQuestion());

        option1.setText(q.getOption1());
        option2.setText(q.getOption2());
        option3.setText(q.getOption3());
        option4.setText(q.getOption4());

        buttonGroup.clearSelection();

        if (answers[currentQuestion] == 1) {
            option1.setSelected(true);
        } else if (answers[currentQuestion] == 2) {
            option2.setSelected(true);
        } else if (answers[currentQuestion] == 3) {
            option3.setSelected(true);
        } else if (answers[currentQuestion] == 4) {
            option4.setSelected(true);
        }
    }

    // Save selected answer
    void saveAnswer() {

        if (option1.isSelected()) {
            answers[currentQuestion] = 1;
        } else if (option2.isSelected()) {
            answers[currentQuestion] = 2;
        } else if (option3.isSelected()) {
            answers[currentQuestion] = 3;
        } else if (option4.isSelected()) {
            answers[currentQuestion] = 4;
        }
    }

    // Button actions
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == nextButton) {

            saveAnswer();

            if (currentQuestion < questions.size() - 1) {
                currentQuestion++;
                displayQuestion();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "This is the last question.");
            }

        } else if (e.getSource() == previousButton) {

            saveAnswer();

            if (currentQuestion > 0) {
                currentQuestion--;
                displayQuestion();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "This is the first question.");
            }

        } else if (e.getSource() == submitButton) {

            saveAnswer();

            timer.stop();

            calculateResult();
        }
    }

    // Calculate result
    void calculateResult() {

        int correct = 0;

        for (int i = 0; i < questions.size(); i++) {

            if (answers[i] == questions.get(i).getCorrectAnswer()) {
                correct++;
            }
        }

        int total = questions.size();
        int wrong = total - correct;

        double percentage = ((double) correct / total) * 100;

        String result;

        if (percentage >= 50) {
            result = "PASS";
        } else {
            result = "FAIL";
        }

        JOptionPane.showMessageDialog(
                this,
                "ONLINE EXAM RESULT\n\n"
                + "Total Questions : " + total + "\n"
                + "Correct Answers : " + correct + "\n"
                + "Wrong Answers   : " + wrong + "\n"
                + "Percentage       : " + percentage + "%\n"
                + "Result           : " + result,
                "Exam Result",
                JOptionPane.INFORMATION_MESSAGE);

        System.exit(0);
    }

    // Main method
    public static void main(String[] args) {

        new OnlineExam();
    }
}