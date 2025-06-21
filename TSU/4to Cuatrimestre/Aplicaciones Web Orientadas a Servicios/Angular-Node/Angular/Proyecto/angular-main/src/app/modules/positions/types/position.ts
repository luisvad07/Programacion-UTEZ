import { Entity } from "../../../types/entity";
export type Position = Entity<number> & {
  position?: string;
  description?: string;
};
