import { Entity } from '../../../types/entity';
import { Position } from '../../positions/types/position';

export type Personal = Entity<number> & {
  name: string;
  surname: string;
  lastname: string;
  birthday: string;
  salary: number;
  position?: Position;
  user: any
};
