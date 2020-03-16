import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {UsuarioService} from '../../@core/service/usuario.service';
import {takeWhile} from 'rxjs/operators';
import {AlterarSenha} from '../../@core/model/payloads/alterar-senha.model';
import {MustMatch} from '../../@core/functions/must-match.function';

@Component({
  selector: 'app-mudar-senha',
  templateUrl: './alterar-senha.component.html',
  styleUrls: ['./alterar-senha.component.scss']
})
export class AlterarSenhaComponent implements OnInit, OnDestroy {

  private form: FormGroup;
  private alterarSenha: AlterarSenha;
  private alive: boolean;
  private submitted: boolean;
  private loading: boolean;

  constructor(private fb: FormBuilder, private toastr: ToastrService, private usuarioService: UsuarioService) {
    this.alive = true;
    this.alterarSenha = new AlterarSenha();
  }

  ngOnInit() {
    this.form = this.fb.group({
      senhaAtual: [this.alterarSenha.senhaAtual, [Validators.minLength(3), Validators.required]],
      novaSenha: [this.alterarSenha.novaSenha, [Validators.minLength(3), Validators.required]],
      confirmarSenha: ['', [Validators.minLength(3), Validators.required]],
    }, {
      validators: MustMatch('novaSenha', 'confirmarSenha')
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
    this.alterarSenha = this.form.value;
    this.usuarioService.alterarSenha(this.alterarSenha)
      .pipe(takeWhile(() => this.alive))
      .subscribe(response => {
        this.loading = false;
        if (response.success) {
          this.toastr.success(response.message);
        } else {
          this.toastr.error(response.message);
        }
      }, error => {
        console.log(error);
        this.loading = false;
      });
  }

  ngOnDestroy(): void {
    this.alive = false;
  }
}
