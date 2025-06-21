import { Entity } from "src/app/types/entity";

export type Position = Entity<number> & {
    position?: String;
    description?: String;
}