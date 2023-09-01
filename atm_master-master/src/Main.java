import atm.service.AccountService;
import atm.service.impl.AccountServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        AccountServiceImpl accountService1=new AccountServiceImpl();
        accountService1.singUp("Aidai","Ysmailova");
        accountService1.singUp("Aizirek","Toktosunova");
        accountService1.singUp("Aidana","Kamchybekovna");
        accountService1.account();




    }}
