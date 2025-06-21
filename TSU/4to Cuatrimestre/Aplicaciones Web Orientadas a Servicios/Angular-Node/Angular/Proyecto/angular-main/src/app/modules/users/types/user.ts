import { Entity } from "../../../types/entity";
export type User = Entity<number> & {
  email: string;
};
