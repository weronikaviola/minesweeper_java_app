package logic;

import javax.swing.*;
import java.awt.*;

public class GameHeader {
  int mines;
  JPanel header;
  JTextField flagsLeft;
  JButton faceBtn;
  Game game;

  public GameHeader(Game g) {
    this.game = g;
    initialize();
  }

  public JPanel getHeader() {
    return header;
  }

  private void initialize() {
    Font clocksFont = new Font("helvetica", Font.BOLD, 24);
    flagsLeft = new JTextField("", 3);
    flagsLeft.setFont(clocksFont);
    flagsLeft.setEditable(false);
    setFlagsLeft();
    faceBtn = new JButton("");
    faceBtn.setPreferredSize(new Dimension(50, 50));

    BorderLayout layout = new BorderLayout();
    header = new JPanel(layout);
    header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    header.add(BorderLayout.LINE_START, flagsLeft);
    header.add(BorderLayout.CENTER, faceBtn);
  }

  public void setFlagsLeft() {
    int left = this.game.getFlagsLeft();
    String text = String.format("%1$" + 3 + "s", Integer.toString(left)).replace(' ', '0');
    flagsLeft.setText(text);
  }

  public void setGameOver() {
    faceBtn.setText("game over");
  }

  public void setWon() {
    faceBtn.setText("you win");
  }
}