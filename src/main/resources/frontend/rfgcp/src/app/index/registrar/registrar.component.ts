import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Usuario} from '../../@core/model/usuario.model';
import {UsuarioService} from '../../@core/service/usuario.service';
import {ToastrService} from 'ngx-toastr';
import {takeWhile} from 'rxjs/operators';
import {MustMatch} from '../../@core/functions/must-match.function';
import {Router} from '@angular/router';

@Component({
  selector: 'app-registrar',
  templateUrl: './registrar.component.html',
  styleUrls: ['./registrar.component.scss', '../index.component.scss']
})
export class RegistrarComponent implements OnInit, OnDestroy {

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
      email: [this.usuario.nome, [Validators.email, Validators.required]],
      confirmarSenha: ['', [Validators.required]],
      senha: [this.usuario.senha, [Validators.minLength(3), Validators.required]],
    }, {
      validators: MustMatch('senha', 'confirmarSenha')
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
    this.usuarioService.registrar(this.usuario)
      .pipe(takeWhile(() => this.alive))
      .subscribe(response => {
        this.loading = false;
        if (response.success) {
          this.toastr.success(response.message, 'Sucesso');
          this.usuarioService.redirectLogin();
        } else {
          this.toastr.error(response.message, 'Erro');
        }
      }, error => {
        this.loading = false;
      });

  }

  ngOnDestroy(): void {
    this.alive = false;
  }
}
