import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Ser")
public class Ser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // URLパラメータから "hear" を取得
        String hear = request.getParameter("hear");
        if (hear == null || hear.isEmpty()) {
            hear = "聞いてよ"; // デフォルト値
        }

        // 画像を表示する
        if ("image".equals(request.getParameter("type"))) {
            displayImage(response);
            return; // HTML 出力と分離
        }

        // 通常のHTMLレスポンスを生成
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

      out.println("<html>");
      out.println("<head>");
      out.println("<title>ねぎらいうさぎ</title>");
      out.println("<style>");
      out.println(".speech-bubble {"
                + "position: relative;"
                + "background: #FFFFE0;"  // 吹き出し背景：薄い黄色
                + "color: black;"          // 文字色：黒
                + "border-radius: 15px;"
                + "padding: 10px 20px;"
                + "width: 300px;"
                + "margin: 20px auto;"
                + "text-align: center;"
                + "font-size: 18px;"
                + "margin-bottom: 20px;" // 吹き出し間の間隔を設定
                + "}");

        // 吹き出しの右向きの三角形を表示
        out.println(".speech-bubble::after {"
                + "content: '';"
                + "position: absolute;"
                + "bottom: -20px;"
                + "left: 20px;" // 吹き出しの位置を調整
                + "border-width: 20px 20px 0;"
                + "border-style: solid;"
                + "border-color: #FFFFE0 transparent transparent transparent;"
                + "}");

        // 吹き出しが左側に表示されるスタイル
        out.println(".speech-bubble-left {"
                + "position: relative;"
                + "background: #FFEBF2;"
                + "color: black;"
                + "border-radius: 15px;"
                + "padding: 10px 20px;"
                + "width: 300px;"
                + "margin: 20px auto;"
                + "text-align: center;"
                + "font-size: 18px;"
                + "display: inline-block;" 
                + "clear: both;" // 吹き出し間に隙間を作る
                + "}");


        // 吹き出しの左向きの三角形を表示
       out.println(".speech-bubble-left::after {"
                + "content: '';"
                + "position: absolute;"
                + "top: -20px;"  // 吹き出しの矢印を上に表示
                + "left: 20px;" // 吹き出しの位置を調整
                + "border-width: 0 20px 20px 20px;" // 上向きの矢印のサイズ設定
                + "border-style: solid;"
                + "border-color: transparent transparent #FFEBF2 transparent;" // 矢印の色を設定
                + "}");
        
      out.println("</style>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1 style='font-size: 35px;'>✌　ねぎらってくれるよ　✌</h1><br>");

       // 入力フォーム
      out.println("<form method='get' action='Ser'>");
      out.println("<p style='font-size: 20px;'>");
      out.println("<input type='text' name='hear' style='width: 400px; height: 60px;'>");
      out.println("<input type='submit' value='話しかける'style='width: 100px; height: 60px;'>");
      out.println("</p>");
      out.println("</form>");

      // 入力した言葉を吹き出しに
      out.println("<hr>");
      out.println("<div class='speech-bubble-left'>" + hear + "</div>");

      if ("聞いてよ".equals(hear)) { 
        out.println("<div class='speech-bubble'><p>どうしたの？聞くよ！</p></div>");
        } else if ("疲れた".equals(hear)) {
            out.println("<div class='speech-bubble'><p>お疲れ様</p></div>");
        } else if ("しんどい".equals(hear)) {
            out.println("<div class='speech-bubble'><p>何とかなるよ</p></div>");
        } else if ("面倒くさい".equals(hear)) {
            out.println("<div class='speech-bubble'><p>少しだけやってみよう♧</p></div>");
        } else {
            out.println("<div class='speech-bubble'><p>えらいね　お疲れさま！</p></div>");
        }
        
      // 画像を表示する部分
      out.println("<div>");
      out.println("<div>");
      out.println("<img src='Ser?type=image' alt='ねぎらいうさぎ' style='width: 200px; height: auto; display: block; margin-left: auto; margin-right: 600;'>");
      out.println("</div>"); 

      // HTMLを閉じる
      out.println("</body>");
      out.println("</html>");
    }
    //画像をHTTPレスポンスとして返す
    private void displayImage(HttpServletResponse response) throws IOException {
        // 画像のパスを取得
        String imagePath = getServletContext().getRealPath("/chat/src/main/webapp/image/christmas_usagi.png");
        // ログに出力して確認
        System.out.println("Resolved image path: " + imagePath);
        //サーブレットが動作しているアプリケーションのコンテキストを取得=Webアプリケーション内の指定パスの絶対パスを取得
        String imagePath1 = getServletContext().getRealPath("/image/christmas_usagi.png");
        //imagePathで取得した絶対パスをコンソールに出力（デバック用）
        System.out.println("Resolved image path: " + imagePath1);
        
        //Fileクラスのコンストラクタに、ファイルのパスを指定してFileオブジェクトを作成
        File imageFile = new File(imagePath1);
        //画像ファイルが存在しない場合にエラーレスポンスを返す
        if (!imageFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "画像が見つかりません");
            return;
        }
        //HTTPレスポンスのコンテンツタイプ（MIMEタイプ）を "image/png" に設定
        response.setContentType("image/png");
        //try-with-resources文 リソースの解放漏れを防ぐ
        try (FileInputStream fis = new FileInputStream(imageFile);
             OutputStream out = response.getOutputStream()) {
            //バッファの準備 1024バイト
            byte[] buffer = new byte[1024];
            //ファイルを読み込んで書き込むループ
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {//ファイルの終わり（EOF）に達すると -1 を返す
            	//buffer の内容をレスポンスの出力ストリームに書き込み 0 は書き込みの開始位置、bytesRead は書き込むバイト数
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {   //入出力処理でエラーが発生した場合に処理
            e.printStackTrace(); // エラーの詳細をコンソールに出力
            
        }
    }

    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
