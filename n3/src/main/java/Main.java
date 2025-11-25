import algorithm.PathFinder;
import constant.AlgorithmType;
import constant.Messages;
import entity.MazeGenerator;
import entity.Node;
import exception.*;
import ui.ConsoleUI;
import ui.MenuHandler;
import ui.ResultDisplay;

import java.util.List;

/**
 * Chương trình tìm đường đi trong mê cung
 * Hỗ trợ 3 thuật toán: BFS, DFS, A*
 * 
 * Sử dụng các module:
 * - constant: Quản lý messages và config
 * - ui: Xử lý giao diện console
 * - algorithm: Các thuật toán tìm đường
 * - entity: Các đối tượng dữ liệu
 * - exception: Xử lý ngoại lệ
 */
public class Main {
    
    public static void main(String[] args) {
        ConsoleUI consoleUI = new ConsoleUI();
        MenuHandler menuHandler = new MenuHandler(consoleUI);
        ResultDisplay resultDisplay = new ResultDisplay(consoleUI);
        
        try {
            consoleUI.displayHeader();
            
            // Khởi tạo mê cung
            MazeGenerator mazeGen = initializeMaze(consoleUI);
            int[][] maze = mazeGen.getMaze();
            
            consoleUI.displayMaze(maze);
            
            // Lấy điểm bắt đầu và kết thúc
            int[] start = mazeGen.getStart();
            int[] end = mazeGen.getEnd();
            consoleUI.displayStartEndPoints(start, end);
            
            // Chọn thuật toán
            AlgorithmType algorithmType = menuHandler.selectAlgorithmType();
            PathFinder pathFinder = menuHandler.createPathFinder(algorithmType, maze, mazeGen.getWidth());
            
            // Tìm đường đi
            consoleUI.displaySearching(pathFinder.getAlgorithmName());
            long startTime = System.currentTimeMillis();
            List<Node> path = pathFinder.findPath(start[0], start[1], end[0], end[1]);
            long endTime = System.currentTimeMillis();
            
            // Hiển thị kết quả
            resultDisplay.displaySuccess(path, endTime - startTime, start, end);
            
        } catch (InvalidMazeException | InvalidGraphException e) {
            resultDisplay.displayInitializationError(e.getMessage());
        } catch (PathNotFoundException e) {
            resultDisplay.displayPathNotFoundError(e.getMessage());
        } catch (InvalidInputException e) {
            resultDisplay.displayInputError(e.getMessage());
        } catch (Exception e) {
            resultDisplay.displayUnknownError(e.getMessage());
            e.printStackTrace();
        } finally {
            consoleUI.close();
        }
    }
    
    /**
     * Khởi tạo mê cung với kích thước do người dùng nhập
     */
    private static MazeGenerator initializeMaze(ConsoleUI consoleUI) {
        int[] size = consoleUI.getMazeSize();
        int width = size[0];
        int height = size[1];
        
        consoleUI.displayMessage(String.format(Messages.PROCESS_GENERATING_MAZE, width, height));
        MazeGenerator mazeGen = new MazeGenerator(width, height);
        mazeGen.generate();
        
        return mazeGen;
    }
}
