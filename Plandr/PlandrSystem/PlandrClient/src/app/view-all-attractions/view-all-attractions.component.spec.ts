import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllAttractionsComponent } from './view-all-attractions.component';

describe('ViewAllAttractionsComponent', () => {
  let component: ViewAllAttractionsComponent;
  let fixture: ComponentFixture<ViewAllAttractionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAllAttractionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAllAttractionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
