import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class OnlineExamBackend {

    // --------- Question Class ----------
    static class Question {
        String text;
        List<String> options;
        int correctIndex;

        Question(String text, List<String> options, int correctIndex) {
            this.text = text;
            this.options = options;
            this.correctIndex = correctIndex;
        }
    }

    // --------- Exam Engine ------------
    static class ExamEngine {

        List<Question> questions = new ArrayList<>();
        List<Integer> userAnswers = new ArrayList<>();
        int timerSeconds;
        boolean timeUp = false;

        public ExamEngine() {
            loadQuestions();
            for (int i = 0; i < questions.size(); i++)
                userAnswers.add(-1);  // -1 = not answered
        }

        // Load Questions
        private void loadQuestions() {
            questions.add(new Question(
                    "What is the primary purpose of a Thread?",
                    Arrays.asList(
                            "Pause execution",
                            "Allow concurrent execution",
                            "Handle file I/O",
                            "Define class structure"
                    ), 1));

            questions.add(new Question(
                    "Which data structure works on LIFO?",
                    Arrays.asList(
                            "Queue", "Array", "Stack", "Linked List"
                    ), 2));

            questions.add(new Question(
                    "Which mechanism handles runtime errors?",
                    Arrays.asList(
                            "If/Else", "Try...Catch", "Console.log", "Event Listeners"
                    ), 1));

            questions.add(new Question(
                    "Which HTML tag creates a link?",
                    Arrays.asList("<link>", "<href>", "<a>", "<nav>"),
                    2
            ));

            questions.add(new Question(
                    "Converting data to JSON string is:",
                    Arrays.asList("Inheritance", "Polymorphism", "Strings", "Encapsulation"),
                    2
            ));
        }

        // Timer (Thread)
        public void startTimer(int minutes) {
            timerSeconds = minutes * 60;

            Thread timerThread = new Thread(() -> {
                try {
                    while (timerSeconds > 0) {
                        Thread.sleep(1000);
                        timerSeconds--;
                    }
                    timeUp = true;
                } catch (Exception e) {
                    System.out.println("Timer Error: " + e.getMessage());
                }
            });

            timerThread.start();
        }

        // Set user answer
        public void setAnswer(int qIndex, int ansIndex) {
            userAnswers.set(qIndex, ansIndex);
        }

        // Evaluate Score
        public int calculateScore() {
            int score = 0;
            for (int i = 0; i < questions.size(); i++) {
                if (userAnswers.get(i) == questions.get(i).correctIndex)
                    score++;
            }
            return score;
        }

        // Generate Result File (File I/O)
        public String generateResultFile(String userName) {
            String fileName = "exam_results_" + userName.replace(" ", "_") + "_" + System.currentTimeMillis() + ".txt";

            try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {

                pw.println("--- Online Examination System Results ---");
                pw.println("Candidate: " + userName);
                pw.println("Date: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
                pw.println();

                int score = calculateScore();
                int total = questions.size();
                double percent = (score * 100.0) / total;

                pw.println("FINAL SCORE: " + score + " / " + total + " (" + percent + "%)");
                pw.println();
                pw.println("--- Detailed Answers ---");

                for (int i = 0; i < questions.size(); i++) {
                    Question q = questions.get(i);
                    int ua = userAnswers.get(i);

                    pw.println("\nQ" + (i + 1) + ": " + q.text);
                    pw.println("   Your Answer: " + (ua == -1 ? "No Answer" : q.options.get(ua)));
                    pw.println("   Correct Answer: " + q.options.get(q.correctIndex));
                    pw.println("   Result: " + (ua == q.correctIndex ? "CORRECT" : "INCORRECT"));
                }

                return fileName;

            } catch (Exception e) {
                return "Error generating file: " + e.getMessage();
            }
        }
    }

    // -------- Main Method ----------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExamEngine exam = new ExamEngine();

        System.out.println("Enter your name:");
        String name = sc.nextLine();

        exam.startTimer(3);  // 3-minute exam

        for (int i = 0; i < exam.questions.size(); i++) {
            if (exam.timeUp) break;

            Question q = exam.questions.get(i);
            System.out.println("\nQ" + (i + 1) + ": " + q.text);

            for (int j = 0; j < q.options.size(); j++) {
                System.out.println("   " + j + ". " + q.options.get(j));
            }

            System.out.print("Enter answer (0-3): ");
            int ans = sc.nextInt();
            exam.setAnswer(i, ans);
        }

        System.out.println("\nTime's Up or Exam Completed!");
        int finalScore = exam.calculateScore();
        System.out.println("Your Score: " + finalScore);

        String file = exam.generateResultFile(name);
        System.out.println("Result saved as: " + file);
    }
}
