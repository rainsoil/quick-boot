import { VNode } from 'vue'

export type ISlots = {
  header?: ((...args: any[]) => VNode | string | void) | string;
  default?: ((...args: any[]) => VNode | string | void) | string;
}

export type ItemCols = {
  label: string;
  prop: string;
  children?: Array<ItemCols>;
  // slot?: ISlots;
  render?: (...args: any[]) => VNode | number | string;
  // render?: ((...args: any[]) => VNode | string | void) | string;
} & Indexable

export type IPageProps = {
  current: number;
  size: number;
  total: number;
}
