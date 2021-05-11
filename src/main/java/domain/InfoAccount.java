package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class InfoAccount {

    @Id
    private String dni;
    
    private String name;
    
    private String lastName;
    
    private String correo;
    @OneToOne
    private Account account;
    
    public InfoAccount(String dni, String name, String lastName, String correo, Account account) {
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
        this.correo = correo;
        this.account = account;
    }
    
    public InfoAccount(String dni, String name, String lastName, String correo, String usuario, String contrasena) {
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
        this.correo = correo;
        this.account = new Account(usuario, contrasena, this);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Account getaccount() {
        return account;
    }

    public Account setaccount(Account account) {
        this.account = account;
        return account;
    }
    
    
}