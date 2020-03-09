import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Usuario} from '../../@core/model/usuario.model';
import {UsuarioService} from '../../@core/service/usuario.service';
import {takeWhile} from 'rxjs/operators';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css', '../index.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {

  private form: FormGroup;
  private usuario: Usuario;
  private alive: boolean;
  private submitted: boolean;
  private loading: boolean;

  constructor(private fb: FormBuilder, private usuarioService: UsuarioService, private toastr: ToastrService) {
    this.usuario = new Usuario();
    this.alive = true;
  }

  ngOnInit() {
    this.form = this.fb.group({
      nome: [this.usuario.nome, [Validators.minLength(3), Validators.required]],
      senha: [this.usuario.senha, [Validators.minLength(3), Validators.required]],
    });
  }

  get formControls() {
    return this.form.controls;
  }

  submit() {
    this.submitted = true;
    this.loading = true;
    if (this.form.invalid) {
      this.loading = false;
      return;
    }
    this.usuario = this.form.value;
    this.usuarioService.login(this.usuario)
      .pipe(takeWhile(() => this.alive))
      .subscribe(usuario => {
        this.loading = false;
      }, error => {
        this.loading = false;
      });

  }

  ngOnDestroy(): void {
    this.alive = false;
  }
}
