# Chương trình tìm đường đi trong mê cung

## Mô tả
Đề tài “Ứng dụng thuật toán BFS, DFS, A* để giải quyết bài toán tìm đường trong mê cung” được thực hiện với mục tiêu mô phỏng, so sánh và đánh giá hiệu năng của ba thuật toán trong cùng một môi trường thí nghiệm thống nhất. Trong bối cảnh công nghệ thông tin phát triển mạnh mẽ, các bài toán tìm đường tối ưu không chỉ mang ý nghĩa lý thuyết mà còn có giá trị ứng dụng cao, từ trò chơi, điều hướng robot, đến các hệ thống dẫn đường thông minh.

Thông qua việc giải bài toán tìm đường trong mê cung, người dùng có thể trực quan so sánh ưu nhược điểm của từng thuật toán, hiểu rõ hơn về cơ chế hoạt động, đồng thời rèn luyện tư duy thuật toán và khả năng phân tích, đánh giá giải pháp cho các bài toán thực tiễn.

## Cấu trúc project

```
n3/
├── src/main/java/
│   ├── Main.java              # Hàm main chính - Điểm vào chương trình (đã tối ưu)
│   ├── constant/              # Module quản lý hằng số
│   │   ├── Messages.java            # Tất cả messages trong hệ thống
│   │   ├── Config.java              # Các tham số cấu hình
│   │   └── AlgorithmType.java       # Enum các loại thuật toán
│   ├── ui/                    # Module giao diện người dùng
│   │   ├── ConsoleUI.java           # Xử lý input/output console
│   │   ├── MenuHandler.java         # Xử lý menu và navigation
│   │   └── ResultDisplay.java       # Format và hiển thị kết quả
│   ├── algorithm/             # Module thuật toán
│   │   ├── PathFinder.java          # Interface chung cho thuật toán
│   │   ├── AStarAlgorithm.java      # Thuật toán A*
│   │   ├── BFSAlgorithm.java        # Thuật toán BFS
│   │   └── DFSAlgorithm.java        # Thuật toán DFS
│   ├── entity/                # Module entity (các đối tượng)
│   │   ├── Node.java                # Node trong đồ thị/mê cung
│   │   ├── Graph.java               # Đồ thị dạng danh sách kề
│   │   ├── GraphBuilder.java        # Chuyển đổi mê cung thành đồ thị
│   │   └── MazeGenerator.java       # Sinh mê cung ngẫu nhiên
│   └── exception/             # Module xử lý ngoại lệ
│       ├── PathNotFoundException.java     # Không tìm thấy đường đi
│       ├── InvalidMazeException.java      # Mê cung không hợp lệ
│       ├── InvalidGraphException.java     # Đồ thị không hợp lệ
│       └── InvalidInputException.java     # Input không hợp lệ
└── pom.xml
```

##  Các module

### 1. Module Constants
- **Messages**: Quản lý tập trung tất cả messages (UI, errors, exceptions)
- **Config**: Các tham số cấu hình (kích thước mê cung, hiển thị, thuật toán)
- **AlgorithmType**: Enum định nghĩa các loại thuật toán

### 2. Module UI/View
- **ConsoleUI**: Xử lý tất cả input/output với console
- **MenuHandler**: Quản lý menu và điều hướng người dùng
- **ResultDisplay**: Format và hiển thị kết quả đẹp mắt

### 3. Module Algorithm
- **PathFinder Interface**: Interface chung cho các thuật toán tìm đường
- **AStarAlgorithm**: Thuật toán A* với Manhattan distance heuristic
- **BFSAlgorithm**: Thuật toán BFS đảm bảo tìm đường đi ngắn nhất
- **DFSAlgorithm**: Thuật toán DFS tìm nhanh nhưng không đảm bảo tối ưu

### 4. Module Entity
- **Node**: Đại diện cho một điểm trong mê cung/đồ thị
- **Graph**: Đồ thị dạng danh sách kề để lưu trữ mê cung
- **GraphBuilder**: Chuyển đổi mê cung dạng ma trận 2D thành đồ thị
- **MazeGenerator**: Sinh mê cung ngẫu nhiên bằng Recursive Backtracking

### 5. Module Exception
- **PathNotFoundException**: Ném ra khi không tìm thấy đường đi
- **InvalidMazeException**: Ném ra khi mê cung không hợp lệ
- **InvalidGraphException**: Ném ra khi đồ thị không hợp lệ
- **InvalidInputException**: Ném ra khi input từ người dùng không hợp lệ

### 6. Main.java
- Điểm vào chính của chương trình
- Menu tương tác cho người dùng chọn thuật toán
- Xử lý input và hiển thị kết quả
- Xử lý các exception một cách toàn diện

##  Cách chạy chương trình

### Sử dụng Java trực tiếp:
```bash
cd n3
javac -d target/classes -encoding UTF-8 src/main/java/constant/*.java src/main/java/exception/*.java src/main/java/entity/*.java src/main/java/algorithm/*.java src/main/java/ui/*.java src/main/java/Main.java
java -cp target/classes Main
```

### Sử dụng Maven (nếu có):
```bash
cd n3
mvn clean compile
mvn exec:java -Dexec.mainClass="Main"
```

##  Cách sử dụng

1. Chạy chương trình
2. Nhập kích thước mê cung (chiều rộng và chiều cao - số lẻ)
3. Chương trình sẽ tự động tạo mê cung ngẫu nhiên
4. Chọn thuật toán tìm đường:
   - **1**: BFS - Tìm đường ngắn nhất
   - **2**: DFS - Tìm nhanh
   - **3**: A* - Tối ưu với heuristic
5. Xem kết quả hiển thị đường đi tìm được

##  Thông tin hiển thị

Chương trình sẽ hiển thị:
- Mê cung được tạo
- Điểm bắt đầu và điểm kết thúc
- Thuật toán được chọn
- Đường đi tìm được (nếu có)
- Độ dài đường đi
- Số nút đi qua
- Thời gian thực thi

## Phạm vi bài tập lớn

Project này được phát triển trong phạm vi bài tập lớn môn học, với các yêu cầu:
-  Tổ chức mã nguồn khoa học với các module rõ ràng
-  1 hàm main chính để khởi động chương trình
-  Module algorithm chứa các thuật toán tìm đường
-  Module exception xử lý ngoại lệ
-  Module entity chứa các đối tượng dữ liệu
-  Module constant quản lý tập trung messages và config
-  Module ui/view tách biệt logic giao diện

##  Yêu cầu hệ thống

- Java 23 hoặc cao hơn
- Maven 3.x (tùy chọn)

##  Ghi chú

- Thuật toán BFS đảm bảo tìm được đường đi ngắn nhất
- Thuật toán DFS tìm nhanh hơn nhưng không đảm bảo tối ưu
- Thuật toán A* cân bằng giữa tốc độ và tối ưu với Manhattan distance heuristic
- Mê cung được sinh ngẫu nhiên mỗi lần chạy chương trình

## Hướng dẫn đóng góp

Bạn đọc có thể tham khảo, kiểm thử và đóng góp ý kiến cho nội dung báo cáo cũng như mã nguồn bằng cách tạo **Issue** theo các bước sau:


1. Chọn tab `Issues` ở thanh điều hướng trên cùng.
2. Nhấn nút `New issue` để tạo issue mới.
3. Ở phần **Title**, ghi ngắn gọn nội dung góp ý hoặc lỗi phát hiện.
4. Ở phần **Description**, mô tả chi tiết(optional):
   - Ngữ cảnh hoặc phần báo cáo/liên quan trong README.
   - Bước thực hiện (nếu là lỗi chạy chương trình).
   - Kỳ vọng của bạn (mong muốn chỉnh sửa, bổ sung, cải thiện).
5. Nhấn `Submit new issue` để gửi.

Nhóm thực hiện sẽ tiếp nhận, phản hồi và cập nhật lại tài liệu/mã nguồn nếu phù hợp với định hướng của đề tài.
