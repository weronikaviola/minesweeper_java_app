import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import logic.*;

class MineSweeper {
  JFrame frame;
  JPanel mainPanel;
  JToggleButton[][] mainButtons;
  JPanel background;
  GameHeader header;
  Game game;
  public static void main(String[] args) {
    new MineSweeper().go();
  }

  public void go() {
    game = new Game();
    initializeGUI();
    initializeGame();
  }

  private void initializeGUI() {
    frame = new JFrame();
    generateBackground();
    generateGameHeader();
    generateMainPanel();
    generateMenuBar();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  private void initializeGame() {
    game.initialize();
  }

  private void generateBackground() {
    BorderLayout layout = new BorderLayout();
    background = new JPanel(layout);
    background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    frame.getContentPane().add(background);
  }

  private void generateGameHeader() {
    header = new GameHeader(game);
    background.add(BorderLayout.PAGE_START, header.getHeader());
  }

  private void generateMainPanel() {
    int rows = game.fieldSizeR;
    int cols = game.fieldSizeC;
    GridLayout grid = new GridLayout(rows, cols);
    grid.setVgap(1);
    grid.setHgap(1);
    mainPanel = new JPanel(grid);
    mainButtons = new JToggleButton[rows][cols];
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        JToggleButton b = new JToggleButton("");
        b.setPreferredSize(new Dimension(50, 50));
        b.setActionCommand(String.format("%d/%d", r, c));
        b.addActionListener(new ButtonActionListener());
        b.addMouseListener(new ButtonMouseListener(r, c));
        b.setFocusPainted(false);
        mainPanel.add(b);
        mainButtons[r][c] = b;
      }
    }

    background.add(BorderLayout.CENTER, mainPanel);
  }

  private void generateMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu gameMenu = new JMenu("Game");
    JMenu difficulty = new JMenu("Mode");
    gameMenu.add(difficulty);
    JMenuItem easy = new JMenuItem("Easy");
    easy.addActionListener(new MenuListener("easy"));
    JMenuItem medium = new JMenuItem("Medium");
    medium.addActionListener(new MenuListener("medium"));
    JMenuItem expert = new JMenuItem("Expert");
    expert.addActionListener(new MenuListener("expert"));
    difficulty.add(easy);
    difficulty.add(medium);
    difficulty.add(expert);
    menuBar.add(gameMenu);
    frame.setJMenuBar(menuBar);
  }

  private void checkForWin() {
    boolean won = game.isWin();
    if (won) {
      System.out.println("won");
      header.setWon();
    }
  }

  private void makeMove(int r, int c) {
    Field field = game.getField(r, c);
    JToggleButton btn = mainButtons[r][c];
    if (field.type == FieldTypes.MINE) {
      btn.setText("X");
      endGame();
    } else if (!field.isUncovered() && !field.isFlaggedd()) {
      game.revealField(r, c);
      revealField(r, c);
    }
    checkForWin();
  }

  private void refreshBoard() {
    System.out.println("refreshing board");
    for (int r = 0; r < game.fieldSizeR; r++) {
      for (int c = 0; c < game.fieldSizeC; c++) {
        Field field = game.getField(r, c);
        JToggleButton btn = mainButtons[r][c];
        String currText = btn.getText();
        if (field.isUncovered() && field.type == FieldTypes.REGULAR && !field.isFlaggedd() && currText == "") {
          clickAndDisable(r, c);
          int mines = field.getNeighborMines();
          if (mines > 0) {
            btn.setText(Integer.toString(mines));
          } 
        }
      }
    }
  }

  private void clickAndDisable(int r, int c) {
    JToggleButton btn = mainButtons[r][c];
    if (!btn.isSelected()) {
      btn.doClick();
    }
    btn.setEnabled(false);
  }

  private void revealField(int r, int c) {
    refreshBoard();
  }

  private void endGame() {
    revealAllMines();
    header.setGameOver();
    game.gameOver();
  }

  private void plantFlag(int row, int col) {
    game.plantFlag(row, col);
    JToggleButton btn = mainButtons[row][col];
    btn.setText("F");
    header.setFlagsLeft();
  }

  private void removeFlag(int row, int col) {
    game.removeFlag(row, col);
    JToggleButton btn = mainButtons[row][col];
    btn.setText("");
    header.setFlagsLeft();
  }

  private void revealAllMines() {
    int[][] minesArr = game.getMinesArray();
    for (int[] mine : minesArr) {
      int x = mine[0];
      int y = mine[1];

      JToggleButton btn = mainButtons[x][y];
      btn.setText("X");
    }
  }

  class ButtonActionListener implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      String[] command = ev.getActionCommand().split("/");
      int r = Integer.parseInt(command[0]);
      int c = Integer.parseInt(command[1]);
      makeMove(r, c);
    }
  }

  class ButtonMouseListener implements MouseListener {
    int row;
    int col;

    public ButtonMouseListener(int r, int c) {
      this.row = r;
      this.col = c;
    }
    public void mouseClicked(MouseEvent e) {
      int clickedButton = e.getButton();
      Field field = game.getField(row, col);
      boolean canPlantFlag = game.canPlantFlag();
      if (clickedButton == 3) {
        if (!field.isUncovered() && !field.isFlaggedd() && canPlantFlag) {
          plantFlag(this.row, this.col);
        } else if (field.isFlaggedd()) {
          removeFlag(this.row, this.col);
        }
      }
    }

    public void mouseEntered(MouseEvent e) {
      return;
    }

    public void mouseExited(MouseEvent e) {
      return;
    }

    public void mousePressed(MouseEvent e) {
      return;
    }

    public void mouseReleased(MouseEvent e) {
      return;
    }
  }

  public class MenuListener implements ActionListener {
    String level;

    public MenuListener(String l) {
      level = l;
    }

    public void actionPerformed(ActionEvent evt) {
      go();
    }
  }
}