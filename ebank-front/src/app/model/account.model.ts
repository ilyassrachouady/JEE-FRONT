export interface AccountDetails {
  accountID:            string;
  balance:              number;
  currentPage:          number;
  totalPage:           number;
  pageSize:             number;
  accountOperationsDTOS: AccountOperation[];
}

export interface AccountOperation {
  id:            number;
  operationDate: Date;
  amount:        number;
  type:          string;
  description:   string;
}
