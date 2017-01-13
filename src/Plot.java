import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Plot {

    public static void main(String arg[]) throws Exception {
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();

        Scanner sc = new Scanner(new File("round1.csv"));

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] split = line.split(",");
            if (split.length == 2) {
                x.add(Integer.parseInt(split[0]));
                y.add(Integer.parseInt(split[1]));
            }
        }

        final JFrame frame = new JFrame("Point Data Rendering");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final PPanel panel = new PPanel(x, y);
        panel.setPreferredSize(new Dimension(1000, 1000));
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 924, 0);
        slider.setMinorTickSpacing(5);
        slider.setMajorTickSpacing(50);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(slider.createStandardLabels(50));
        frame.add(slider, BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);
        frame.repaint();

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    int val = source.getValue();
                    panel.setShift(val);
                    frame.repaint();
                }
            }
        });
    }

    public static class PPanel extends JPanel {
        ArrayList<Integer> x;
        ArrayList<Integer> y;
        int shift;

        public PPanel(ArrayList<Integer> x, ArrayList<Integer> y) {
            this.x = x;
            this.y = y;
        }

        public void setShift(int shift) {
            this.shift = shift;
        }

        @Override
        public void paintComponent(final Graphics g) {
            double a = Math.PI * 2 / x.size();
            for (int i = 0; i < x.size(); i++) {
                int px = (int) (x.get(i));
                int py = (int) (y.get((i + shift) % y.size()));
                g.setColor(Color.GRAY);
                g.fillRect(px, py, 2, 2);

                g.setColor(Color.PINK);
                g.fillRect(i + 30, px, 2, 2);
                g.setColor(Color.CYAN);
                g.fillRect(i + 30, py, 2, 2);

                int xx = (int) (Math.cos(a * i) * x.get(i) / 2) + 500;
                int xy = (int) (Math.sin(a * i) * x.get(i) / 2) + 500;
                int yx = (int) (Math.cos(a * i) * y.get((i + shift) % y.size()) / 2) + 500;
                int yy = (int) (Math.sin(a * i) * y.get((i + shift) % y.size()) / 2) + 500;
                int cx = (int) (Math.cos(a * i) * 300) + 500;
                int cy = (int) (Math.sin(a * i) * 300) + 500;
                g.setColor(Color.GREEN);
                g.fillRect(xx, xy, 2, 2);
                g.setColor(Color.BLUE);
                g.fillRect(yx, yy, 2, 2);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(cx, cy, 1, 1);


            }
        }

    }

}
