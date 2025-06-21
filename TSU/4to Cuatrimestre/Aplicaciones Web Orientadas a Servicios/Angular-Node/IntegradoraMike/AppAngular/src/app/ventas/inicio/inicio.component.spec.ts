import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InicioVentas } from './inicio.component';

describe('InicioComponent', () => {
  let component: InicioVentas;
  let fixture: ComponentFixture<InicioVentas>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InicioVentas ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InicioVentas);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
