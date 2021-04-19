import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        TestDB serv;
        serv = new TestDB();
        System.out.println("Добро пожаловать, введите логин и пароль через /");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = reader.readLine();
        if (!s.equals("")){
            String[] arr = s.trim().split("/",2);
            if(arr.length == 2){
                String res = serv.login(arr[0],arr[1]);
                System.out.println(res);
                if (res.startsWith("Не найден")){
                    System.out.println("Хотите зарегистрироваться?");
                }
            }else {
                System.out.println("Не верный формат логина и пароля, попробуйте еще раз");
            }
        }
    }
}
