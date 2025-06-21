import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MainPositionComponent } from "./main-position/main-position.component";

@NgModule({
  declarations: [MainPositionComponent],
  imports: [CommonModule],
  exports: [MainPositionComponent],
})
export class PositionsModule {}
