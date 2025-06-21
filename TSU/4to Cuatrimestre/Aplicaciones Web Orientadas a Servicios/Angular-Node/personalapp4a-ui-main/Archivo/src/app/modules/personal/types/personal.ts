import { Entity } from "src/app/types/entity";
import { Position } from "./position";

export type Personal = Entity<number> & {
    name: string;
    surname: string;
    lastname: string;
    birthday: string;
    salary: number;
    position?: Position;
    user: any,
    avatar?: string
}