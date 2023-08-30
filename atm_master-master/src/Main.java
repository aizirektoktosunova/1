import atm.service.AccountService;
import atm.service.impl.AccountServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AccountService accountService=new AccountServiceImpl();
        accountService.singUp("Aidai","Ysmailova");
        accountService.singUp("Aizirek","Toktosunova");
        accountService.singIn("Aizirek","Toktosunova" );



    }}
